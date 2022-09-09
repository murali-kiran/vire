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
public class SocialPostRetrievalRepository {

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
    private static final String SEND_TO_TYPE_REQUEST_TYPE = "Request_Type";
    private static final String SEND_TO_TYPE_DESIGNATION = "Designation";
    private static final String SEND_TO_TYPE_COMMUNITY = "Community";
    private static final String SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS = "Field_Profession_Business";
    private static final String SEND_TO_TYPE_PRODUCT_OR_SERVICE = "Product_Service";
    private static final String SEND_TO_TYPE_INTERESTS = "Interests";

    public List<SocialDao> getSocialListBySearch(Long profileId, int pageNumber, int pageSize,
                                                 List<String> categoryFilters, List<String> communityIdFilters) {
        ProfileDao profileDao = profileRepositoryJpa.findById(profileId).get();

        List<String> categoryFiltersToBeApplied = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categoryFilters)) {
            categoryFiltersToBeApplied.addAll(categoryFilters);
        } else if (1 == 2) {
            //TODO default categories from profile
        } else {
            if(profileDao.getPersonalProfile() != null) {
                categoryFiltersToBeApplied.add("Emergency");
                categoryFiltersToBeApplied.add("Employment");
                categoryFiltersToBeApplied.add("Share");
                categoryFiltersToBeApplied.add("Need");
                categoryFiltersToBeApplied.add("Support");
                categoryFiltersToBeApplied.add("Deal");
                categoryFiltersToBeApplied.add("Business");
            }else{
                categoryFiltersToBeApplied.add("Employment");
                categoryFiltersToBeApplied.add("Vacancy");
                categoryFiltersToBeApplied.add("RequestForBidding");
                categoryFiltersToBeApplied.add("Business");
            }
        }
        StringBuilder query = new StringBuilder();
        if (Objects.nonNull(profileDao)) {
            if (profileDao.getPersonalProfile() != null) {
                query.append("SELECT social.* FROM (");
                query.append("SELECT ts.* FROM t_social ts WHERE ts.profile_id="+profileDao.getProfileId());
                query.append("\nUNION ");
                for (String categoryFilter : categoryFiltersToBeApplied) {
                    if (query.toString().contains(")")) {
                        query.append("\nUNION ");
                    }
                    if ("Emergency".equalsIgnoreCase(categoryFilter)) {
                        query.append("SELECT emergency.* FROM (" + emergencyCode(profileDao, communityIdFilters) + " ) AS emergency ");
                    }
                    if ("Employment".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT employment.* FROM (" + employmentCode(profileDao, communityIdFilters) + " ) AS employment");
                    }
                    if ("Share".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT share.* FROM (" + shareCode(profileDao, communityIdFilters) + " ) AS share");
                    }
                    if ("Need".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT need.* FROM (" + needCode(profileDao, communityIdFilters) + " ) AS need");
                    }
                    if ("Support".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT support.* FROM (" + supportCode(profileDao, communityIdFilters) + " ) AS support");
                    }
                    if ("Deal".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT deal.* FROM (" + dealCode(profileDao, communityIdFilters) + " ) AS deal");
                    }
                    if ("Business".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT business.* FROM (" + businessCode(profileDao, communityIdFilters) + " ) AS business");
                    }
                }
                query.append(") AS social ORDER BY social.updated_time DESC");
            } else {

                query.append("SELECT social.* FROM (");
                query.append("SELECT ts.* FROM t_social ts WHERE ts.profile_id="+profileDao.getProfileId());
                query.append("\nUNION ")
                ;
                for (String categoryFilter : categoryFiltersToBeApplied) {
                    if (query.toString().contains(")")) {
                        query.append("\nUNION ");
                    }
                    if ("Employment".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT employment.* FROM (" + employmentCode(profileDao, communityIdFilters) + " ) AS employment");
                    }
                    if ("Vacancy".equalsIgnoreCase(categoryFilter)) {
                        query.append("SELECT vacancy.* FROM (" + vacancyCode(profileDao, communityIdFilters) + " ) AS vacancy ");
                    }
                    if ("RequestForBidding".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT requestforbidding.* FROM (" + requestForBiddingCode(profileDao, communityIdFilters) + " ) AS requestforbidding");
                    }
                    if ("Business".equalsIgnoreCase(categoryFilter)) {
                        query.append("\nSELECT business.* FROM (" + businessCode(profileDao, communityIdFilters) + " ) AS business");
                    }
                }
                query.append(") AS social ORDER BY social.updated_time DESC");
            }
        } else {
            return new ArrayList<>();
        }
        log.info("Generated Query: {}", query);

        /*return entityManager.createNativeQuery(query.toString(), SocialDao.class)
                .getResultList();*/
        var queryObject = entityManager.createNativeQuery(query.toString(), SocialDao.class);
        queryObject.setFirstResult((pageNumber - 1) * pageSize);
        queryObject.setMaxResults(pageSize);
        return queryObject.getResultList();
    }

    private String emergencyCode(ProfileDao profileDao, List<String> communityIdFilters) {

        var emergencyQuery = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Emergency'";
        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        emergencyQuery.append(" ( ").append(addressQuery).append(" ) as address ");
        if (profileDao.getPersonalProfile() != null) {
            var bloodGroupQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_BLOOD_GROUP, profileDao.getPersonalProfile().getBloodGroup());
            emergencyQuery.append(" JOIN ( ").append(bloodGroupQuery).append(" ) as blood_group on address.social_id = blood_group.social_id ");
            var bloodWillingNessQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_REQUEST_TYPE, profileDao.getPersonalProfile().getBloodDonateWillingness().toString().toLowerCase());
            emergencyQuery.append(" JOIN ( ").append(bloodWillingNessQuery).append(" ) as request_type on address.social_id = request_type.social_id ");
        }
        return emergencyQuery.toString();
    }
    private String vacancyCode(ProfileDao profileDao, List<String> communityIdFilters) {

        var vacancyQuery = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Vacancy'";
        AddressDao addressDao = profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        vacancyQuery.append(" ( ").append(addressQuery).append(" ) as address ");
        var communityProfileList = communityIdFilters;
        if (CollectionUtils.isEmpty(communityIdFilters)) {
            var communityProfiles = communityProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId() + "").collect(Collectors.toList());
            communityProfileList.add("all");
        }

        var communityQuery = frameCommunityQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        vacancyQuery.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        return vacancyQuery.toString();
    }
    private String requestForBiddingCode(ProfileDao profileDao, List<String> communityIdFilters) {

        var requestForBiddingQuery = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Vacancy'";
        AddressDao addressDao = profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        requestForBiddingQuery.append(" ( ").append(addressQuery).append(" ) as address ");
        var communityProfileList = communityIdFilters;
        if (CollectionUtils.isEmpty(communityIdFilters)) {
            var communityProfiles = communityProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId() + "").collect(Collectors.toList());
            communityProfileList.add("all");
        }

        var communityQuery = frameCommunityQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        requestForBiddingQuery.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        return requestForBiddingQuery.toString();
    }

    private String employmentCode(ProfileDao profileDao, List<String> communityIdFilters) {

        var query = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Employment'";

        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        query.append(" ( ").append(addressQuery).append(" ) as address ");

        if (profileDao.getPersonalProfile() != null) {
            var designationQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_DESIGNATION, profileDao.getPersonalProfile().getDesignation());
            query.append(" JOIN ( ").append(designationQuery).append(" ) as designation on address.social_id = designation.social_id ");
            var fieldProfessionBusinessQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS, profileDao.getPersonalProfile().getFieldProfessionBusiness());
            query.append(" JOIN ( ").append(fieldProfessionBusinessQuery).append(" ) as fpb on address.social_id = fpb.social_id ");

            if (CollectionUtils.isEmpty(profileDao.getPersonalProfile().getInterests())) {
                var interestQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_INTERESTS, new ArrayList<>());
                query.append(" JOIN ( ").append(interestQuery).append(" ) as interest on address.social_id = interest.social_id ");
            } else {
                var interess = profileDao.getPersonalProfile().getInterests().stream().map(x -> x.getInterest()).collect(Collectors.toList());
                var interestQuery = frameQuery(emergencyCategoryId, SEND_TO_TYPE_INTERESTS, interess);
                query.append(" JOIN ( ").append(interestQuery).append(" ) as interest on address.social_id = interest.social_id ");
            }
        }
        var communityProfileList = communityIdFilters;
        if (CollectionUtils.isEmpty(communityIdFilters)) {
            var communityProfiles = communityProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId() + "").collect(Collectors.toList());
            communityProfileList.add("all");
        }

        var communityQuery = frameCommunityQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        query.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        return query.toString();
    }

    private String shareCode(ProfileDao profileDao, List<String> communityIdFilters) {
        return shareNeedSupportDealCode(profileDao, "'Share'", communityIdFilters);
    }

    private String needCode(ProfileDao profileDao, List<String> communityIdFilters) {
        return shareNeedSupportDealCode(profileDao, "'Need'", communityIdFilters);
    }

    private String supportCode(ProfileDao profileDao, List<String> communityIdFilters) {
        return shareNeedSupportDealCode(profileDao, "'Support'", communityIdFilters);
    }

    private String dealCode(ProfileDao profileDao, List<String> communityIdFilters) {
        return shareNeedSupportDealCode(profileDao, "'Deal'", communityIdFilters);
    }

    private String shareNeedSupportDealCode(ProfileDao profileDao, String categoryType, List<String> communityIdFilters) {

        var query = new StringBuffer("SELECT address.* FROM ");
        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(categoryType, addressDao);
        query.append(" ( ").append(addressQuery).append(" ) as address ");

        var communityProfileList = communityIdFilters;
        if (CollectionUtils.isEmpty(communityIdFilters)) {
            var communityProfiles = communityProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId() + "").collect(Collectors.toList());
            communityProfileList.add("all");
        }

        var communityQuery = frameCommunityQuery(categoryType, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        query.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        if (profileDao.getPersonalProfile() != null) {
            var designationQuery = frameQuery(categoryType, SEND_TO_TYPE_DESIGNATION, profileDao.getPersonalProfile().getDesignation());
            query.append(" JOIN ( ").append(designationQuery).append(" ) as designation on address.social_id = designation.social_id ");

            var fieldProfessionBusinessQuery = frameQuery(categoryType, SEND_TO_TYPE_FIELD_PROFESSION_BUSINESS, profileDao.getPersonalProfile().getFieldProfessionBusiness());
            query.append(" JOIN ( ").append(fieldProfessionBusinessQuery).append(" ) as fpb on address.social_id = fpb.social_id ");
        }
        return query.toString();
    }

    private String businessCode(ProfileDao profileDao, List<String> communityIdFilters) {

        var query = new StringBuffer("SELECT address.* FROM ");
        var emergencyCategoryId = "'Business'";

        AddressDao addressDao = profileDao.getPersonalProfile() != null ? profileDao.getPersonalProfile().getPresentAddress() : profileDao.getFirmProfile().getAddress();
        var addressQuery = frameAddressQuery(emergencyCategoryId, addressDao);
        query.append(" ( ").append(addressQuery).append(" ) as address ");


        var communityProfileList = communityIdFilters;
        if (CollectionUtils.isEmpty(communityIdFilters)) {
            var communityProfiles = communityProfileRepositoryJpa.findAllByProfileId(profileDao.getProfileId());
            communityProfileList = communityProfiles.stream().map(c -> c.getCommunityId() + "").collect(Collectors.toList());
            communityProfileList.add("all");
        }

        var communityQuery = frameCommunityQuery(emergencyCategoryId, SEND_TO_TYPE_COMMUNITY, communityProfileList);
        query.append(" JOIN ( ").append(communityQuery).append(" ) as community on address.social_id = community.social_id ");
        if (profileDao.getPersonalProfile() != null) {
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

        StringBuffer cityQuery = new StringBuffer(String.format(BASIC_QUERY, categoryId));
        cityQuery.append(" WHERE");
        cityQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_CITY, "all"));

        if (!StringUtils.isEmpty(city)) {
            cityQuery.append(" OR");
            cityQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_CITY, city));
        }

        StringBuffer districtQuery = new StringBuffer(String.format(BASIC_QUERY, categoryId));
        districtQuery.append(" WHERE");
        districtQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_DIST, "all"));

        if (!StringUtils.isEmpty(district)) {
            districtQuery.append(" OR");
            districtQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_DIST, district));
        }

        StringBuffer stateQuery = new StringBuffer(" SELECT state.* FROM ( ");
        stateQuery.append(String.format(BASIC_QUERY, categoryId));
        stateQuery.append(" WHERE");
        stateQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_STATE, state))
                .append(" ) as state JOIN ( ")
                .append(districtQuery)
                .append(" ) as dist ON state.social_id = dist.social_id JOIN ( ")
                .append(cityQuery)
                .append(" ) as city ON state.social_id = city.social_id");

        return stateQuery;
    }

    private StringBuffer frameQuery(String categoryId, String sentToType, String value) {

        if (value == null) {
            StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryId));
            sb.append(" WHERE");
            sb.append(String.format(COMMON_WHERE, sentToType, "all"));
            return sb;
        } else {
            return frameQuery(categoryId, sentToType, Arrays.asList(value));
        }
    }

    private StringBuffer frameQuery(String categoryId, String sentToType, List<String> values) {

        StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryId));
        sb.append(" WHERE");
        sb.append(String.format(COMMON_WHERE, sentToType, "all"));

        if (!CollectionUtils.isEmpty(values)) {
            for (String value : values) {
                sb.append(" OR");
                sb.append(String.format(COMMON_WHERE, sentToType, value));
            }
        }

        return sb;
    }

    private StringBuffer frameCommunityQuery(String categoryId, String sentToType, List<String> values) {

        StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryId));
        sb.append(" WHERE");

        if (!CollectionUtils.isEmpty(values)) {
            for (String value : values) {
                if (!sb.toString().endsWith("WHERE")) {
                    sb.append(" OR ");
                }
                sb.append(String.format(COMMON_WHERE, sentToType, value));
            }
        }

        return sb;
    }
}
