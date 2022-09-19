package com.vire.repository;

import com.vire.dao.AddressDao;
import com.vire.dao.FeedsDao;
import com.vire.dao.ProfileDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FeedPostRetrievalRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;

    @Autowired
    CommunityProfileRepositoryJpa communityProfileRepositoryJpa;

    @Autowired
    ChannelProfileRepositoryJpa channelProfileRepositoryJpa;

    private static final String BASIC_QUERY = "SELECT s.* FROM t_feeds s " +
            "JOIN t_feeds_send_to sst ON s.feed_id = sst.feed_id " ;
    private static final String COMMON_WHERE = " ( sst.`type` ='%s' AND sst.value='%s' )";

    private static final String SEND_TO_TYPE_LOCATION_CITY = "location_city";
    private static final String SEND_TO_TYPE_LOCATION_DIST = "location_district";
    private static final String SEND_TO_TYPE_LOCATION_STATE = "location_state";
    private static final String SEND_TO_TYPE_COMMUNITY = "community";
    private static final String SEND_TO_TYPE_CHANNEL = "channel";

    public List<FeedsDao> getFeedListBySearch(Long profileId, int pageNumber, int pageSize) {
        ProfileDao profileDao = profileRepositoryJpa.findById(profileId).get();
//        var channelIdFilters = channelProfileRepositoryJpa.findAllByProfileId(profileId).stream().map(c -> c.getChannelId()+"").collect(Collectors.toList());
//        var communityIdFilters = communityProfileRepositoryJpa.findAllByProfileId(profileId).stream().map(c -> c.getCommunityId()+"").collect(Collectors.toList());

        //List<String> categoryFiltersToBeApplied = new ArrayList<>();

        StringBuilder query = new StringBuilder();
        if (Objects.nonNull(profileDao)) {
                query.append("SELECT feed.* FROM (SELECT s.* FROM t_feeds s where s.profile_id="+profileId+" UNION ");
                query.append(communityChannelCode(profileDao,new ArrayList<>(), new ArrayList<>()));

                query.append(") AS feed ORDER BY feed.updated_time DESC");
        } else {
            return new ArrayList<>();
        }
        var finalQuery = query.toString().replaceAll("__profile_id__",""+profileId);
        log.info("Generated Query: {}", finalQuery);

        /*return entityManager.createNativeQuery(query.toString(), FeedDao.class)
                .getResultList();*/
        var queryObject = entityManager.createNativeQuery(finalQuery, FeedsDao.class);
        queryObject.setFirstResult((pageNumber - 1) * pageSize);
        queryObject.setMaxResults(pageSize);
        return queryObject.getResultList();
    }

    private String communityChannelCode(ProfileDao profileDao, List<String> communityIdFilters, List<String> channelIdFilters) {


        var communityChannelQuery = new StringBuffer(" ");
        /*var communityChannelQuery = new StringBuffer("SELECT address.* FROM ");
        AddressDao addressDao = profileDao.getFirmProfile()!=null ? profileDao.getFirmProfile().getAddress() : profileDao.getPersonalProfile().getPresentAddress();
        var addressQuery = frameAddressQuery(addressDao);
        communityChannelQuery.append(" ( ").append(addressQuery).append(" ) as address ");*/
        var communityProfileList = communityIdFilters;
        if (CollectionUtils.isEmpty(communityIdFilters)) {
            var communityProfiles = communityProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId() + "").collect(Collectors.toList());
            communityProfileList.add("all");
        }
        var channelProfileList = channelIdFilters;
        if (CollectionUtils.isEmpty(channelProfileList)) {
            var channelProfiles = channelProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            channelProfileList = channelProfiles.stream().map(c -> c.getChannelId() + "").collect(Collectors.toList());
            channelProfileList.add("all");
        }

        var communityQuery = frameQuery(SEND_TO_TYPE_COMMUNITY, communityProfileList);
        var channelQuery = frameQuery(SEND_TO_TYPE_CHANNEL, channelProfileList);
       // communityChannelQuery.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.feed_id = community.feed_id ");
        communityChannelQuery.append(" ( ").append(communityQuery).append("  ");
     //   communityChannelQuery.append(" JOIN ( ").append(channelQuery).append(" ) as channel on address.feed_id = channel.feed_id ");
        communityChannelQuery.append(") UNION ( ").append(channelQuery).append(") ");
        communityChannelQuery.append( " UNION ( SELECT s.* FROM t_feeds s where " +
                " ( s.send_to_followers=1 AND ( s.profile_id in (select pf.profile_id from profile_followers pf where pf.follower_id = __profile_id__)))" +
                ")  ");
        return communityChannelQuery.toString();
    }
    
    private StringBuffer frameAddressQuery(AddressDao address) {

        var city = address.getCityTownVillage();
        var district = address.getDistrict();
        var state = address.getState();

        StringBuffer sb = new StringBuffer(BASIC_QUERY);
        sb.append(" WHERE");
        sb.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_CITY, "all"));
        sb.append(" OR");
        sb.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_DIST, "all"));

        if (!StringUtils.isEmpty(city)) {
            sb.append(" OR");
            sb.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_CITY, city));
        }

        if (!StringUtils.isEmpty(district)) {
            sb.append(" OR");
            sb.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_DIST, district));
        }

        StringBuffer stateQuery = new StringBuffer(" SELECT state.* FROM ( ");
        stateQuery.append(BASIC_QUERY);
        stateQuery.append(" WHERE");
        stateQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_STATE, state))
                .append(" ) as state JOIN ( ")
                .append(sb)
                .append(" ) as city ON state.feed_id = city.feed_id");

        return stateQuery;
    }

    private StringBuffer frameQuery(String sentToType, String value) {

        if (value == null) {
            StringBuffer sb = new StringBuffer(BASIC_QUERY);
            sb.append(" WHERE");
            sb.append(String.format(COMMON_WHERE, sentToType, "all"));
            return sb;
        } else {
            return frameQuery(sentToType, Arrays.asList(value));
        }
    }

    private StringBuffer frameQuery( String sentToType, List<String> values) {

        StringBuffer sb = new StringBuffer(BASIC_QUERY);
        sb.append(" WHERE ");

        sb.append(String.format(COMMON_WHERE, sentToType, "all"));

        if (!CollectionUtils.isEmpty(values)) {
            for (String value : values) {
                sb.append(" OR");
                sb.append(String.format(COMMON_WHERE, sentToType, value));
            }
        }

        return sb;
    }
}
