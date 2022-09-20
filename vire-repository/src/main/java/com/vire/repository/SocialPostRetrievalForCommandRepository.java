package com.vire.repository;

import com.vire.dao.AddressDao;
import com.vire.dao.ProfileDao;
import com.vire.dao.SocialDao;
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
public class SocialPostRetrievalForCommandRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;

    @Autowired
    CommunityProfileRepositoryJpa communityProfileRepositoryJpa;

    private static final String BASIC_QUERY = "SELECT s.* FROM t_social s " +
            "JOIN t_social_post_send_to sst ON s.social_id = sst.social_id " +
            "AND s.category_id IN (select social_category_master_id from social_category_master where category in (%s))";
    private static final String COMMON_WHERE = " ( sst.`type` ='%s' AND sst.value='%s' )";

    private static final String SEND_TO_TYPE_LOCATION_CITY = "location_city";
    private static final String SEND_TO_TYPE_LOCATION_DIST = "location_district";
    private static final String SEND_TO_TYPE_LOCATION_STATE = "location_state";
    private static final String SEND_TO_TYPE_BLOOD_GROUP = "Blood_Group";
    private static final String SEND_TO_TYPE_DESIGNATION = "Designation";
    private static final String SEND_TO_TYPE_COMMUNITY = "Community";
    private static final String SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS = "Field_Profession_Business";
    private static final String SEND_TO_TYPE_PRODUCT_OR_SERVICE = "Product_Service";
    private static final String SEND_TO_TYPE_INTERESTS = "Interests";

    public List<SocialDao> getSocialListBySearch(Long profileId, int pageNumber, int pageSize) {
        ProfileDao profileDao = profileRepositoryJpa.findById(profileId).get();
        
        StringBuilder query = new StringBuilder();
        if (Objects.nonNull(profileDao)) {
            if(profileDao.getPersonalProfile() != null) {
                query.append("SELECT social.* FROM (");
                query.append("SELECT emergency.* FROM (" + emergencyCode(profileDao) + " ) AS emergency ");
                query.append("\nUNION ");
                query.append("\nSELECT employment.* FROM (" + employmentCode(profileDao) + " ) AS employment");
                query.append("\nUNION ");
                query.append("\nSELECT shareNeedSupportDeal.* FROM (" + shareNeedSupportDealCode(profileDao) + " ) AS shareNeedSupportDeal");
                query.append("\nUNION ");
                query.append("\nSELECT business.* FROM (" + businessCode(profileDao) + " ) AS business");
                query.append(") AS social");
            }else{
                query.append("SELECT social.* FROM (");
                query.append("SELECT emergency.* FROM (" + emergencyCode(profileDao) + " ) AS emergency ");
                query.append("\nUNION ");
                query.append("\nSELECT employment.* FROM (" + employmentCode(profileDao) + " ) AS employment");
                query.append("\nUNION ");
                query.append("\nSELECT shareNeedSupportDeal.* FROM (" + shareNeedSupportDealCode(profileDao) + " ) AS shareNeedSupportDeal");
                query.append("\nUNION ");
                query.append("\nSELECT business.* FROM (" + businessCode(profileDao) + " ) AS business");
                query.append(") AS social ORDER BY social.updated_time DESC");
            }
        }else {
            return new ArrayList<>();
        }
        log.info("Generated Query: {}", query);

        /*return entityManager.createNativeQuery(query.toString(), SocialDao.class)
                .getResultList();*/
        var queryObject = entityManager.createNativeQuery(query.toString(), SocialDao.class);
        queryObject.setFirstResult((pageNumber-1)*pageSize);
        queryObject.setMaxResults(pageSize);
        return queryObject.getResultList();
    }

    private String emergencyCode(ProfileDao profileDao) {

        var emergencyQuery = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Emergency'";
        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        emergencyQuery.append(" ( ").append(addressQuery).append(" ) as address ");
        if(profileDao.getPersonalProfile() != null) {
            var bloodGroupQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_BLOOD_GROUP, profileDao.getPersonalProfile().getBloodGroup());
            emergencyQuery.append(" JOIN ( ").append(bloodGroupQuery).append(" ) as blood_group on address.social_id = blood_group.social_id ");
        }
        return emergencyQuery.toString();
    }

    private String employmentCode(ProfileDao profileDao) {

        var query = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Employment'";

        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        query.append(" ( ").append(addressQuery).append(" ) as address ");

        if(profileDao.getPersonalProfile() != null) {
            var designationQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_DESIGNATION, profileDao.getPersonalProfile().getDesignation());
            query.append(" JOIN ( ").append(designationQuery).append(" ) as designation on address.social_id = designation.social_id ");
            var fieldProfessionBusinessQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS, profileDao.getPersonalProfile().getFieldProfessionBusiness());
            query.append(" JOIN ( ").append(fieldProfessionBusinessQuery).append(" ) as fpb on address.social_id = fpb.social_id ");

            if(CollectionUtils.isEmpty(profileDao.getPersonalProfile().getInterests())){
                var interestQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_INTERESTS, new ArrayList<>());
                query.append(" JOIN ( ").append(interestQuery).append(" ) as interest on address.social_id = interest.social_id ");
            }else {
                var interess = profileDao.getPersonalProfile().getInterests().stream().map(x -> x.getInterest()).collect(Collectors.toList());
                var interestQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_INTERESTS, interess);
                query.append(" JOIN ( ").append(interestQuery).append(" ) as interest on address.social_id = interest.social_id ");
            }
        }
        var communityProfiles = communityProfileRepositoryJpa.findAllByProfileIdAndStatus(profileDao.getProfileId(), "Accepted");
        var communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId()+"").collect(Collectors.toList());

        var communityQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        query.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        return query.toString();
    }

    private String shareNeedSupportDealCode(ProfileDao profileDao) {

        var query = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Employment','Share','Need','Support','Deal'";
        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        query.append(" ( ").append(addressQuery).append(" ) as address ");

        var communityProfiles = communityProfileRepositoryJpa.findAllByProfileIdAndStatus(profileDao.getProfileId(), "Accepted");
        var communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId()+"").collect(Collectors.toList());

        var communityQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        query.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        if(profileDao.getPersonalProfile() != null) {
            var designationQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_DESIGNATION, profileDao.getPersonalProfile().getDesignation());
            query.append(" JOIN ( ").append(designationQuery).append(" ) as designation on address.social_id = designation.social_id ");

            var fieldProfessionBusinessQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS, profileDao.getPersonalProfile().getFieldProfessionBusiness());
            query.append(" JOIN ( ").append(fieldProfessionBusinessQuery).append(" ) as fpb on address.social_id = fpb.social_id ");
        }
        return query.toString();
    }

    private String businessCode(ProfileDao profileDao) {

        var query = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Business'";

        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        query.append(" ( ").append(addressQuery).append(" ) as address ");


        var communityProfiles = communityProfileRepositoryJpa.findAllByProfileIdAndStatus(profileDao.getProfileId(), "Accepted");
        var communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId()+"").collect(Collectors.toList());

        var communityQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        query.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        if(profileDao.getPersonalProfile() != null) {
            var productOrServiceQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_PRODUCT_OR_SERVICE, profileDao.getPersonalProfile().getProductOrService());
            query.append(" JOIN ( ").append(productOrServiceQuery).append(" ) as product on address.social_id = product.social_id ");

            var fieldProfessionBusinessQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS, profileDao.getPersonalProfile().getFieldProfessionBusiness());
            query.append(" JOIN ( ").append(fieldProfessionBusinessQuery).append(" ) as fpb on address.social_id = fpb.social_id ");
        }
        return query.toString();
    }

    private StringBuffer frameAddressQuery(String categoryId, AddressDao address) {

        var city = address.getCityTownVillage();
        var district = address.getDistrict();
        var state = address.getState();

        StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryId));
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

        StringBuffer stateQuery = new StringBuffer( " SELECT state.* FROM ( ");
        stateQuery.append(String.format(BASIC_QUERY, categoryId));
        stateQuery.append(" WHERE");
        stateQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_STATE, state))
                .append(" ) as state JOIN ( ")
                .append(sb)
                .append(" ) as city ON state.social_id = city.social_id");

        return stateQuery;
    }

    private StringBuffer frameQuery(String categoryId, String sentToType, String value) {

        if(value == null){
            StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryId));
            sb.append(" WHERE");
            sb.append(String.format(COMMON_WHERE, sentToType, "all"));
            return sb;
        }else {
            return frameQuery(categoryId, sentToType, Arrays.asList(value));
        }
    }

    private StringBuffer frameQuery(String categoryId, String sentToType, List<String> values) {

        StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryId));
        sb.append(" WHERE");
        sb.append(String.format(COMMON_WHERE, sentToType, "all"));

        if (!CollectionUtils.isEmpty(values)) {
            for(String value: values) {
                sb.append(" OR");
                sb.append(String.format(COMMON_WHERE, sentToType, value));
            }
        }

        return sb;
    }
}
