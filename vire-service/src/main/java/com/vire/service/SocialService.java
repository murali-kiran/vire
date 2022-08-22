package com.vire.service;

import com.vire.model.request.SocialRequest;
import com.vire.model.response.*;
import com.vire.repository.FileRepository;
import com.vire.repository.SocialPostRetrievalRepository;
import com.vire.repository.SocialRepository;
import com.vire.utils.Snowflake;
import com.vire.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SocialService {

    @Autowired
    Snowflake snowflake;

    @Autowired
    SocialRepository socialRepo;

    @Autowired
    FileRepository socialImageRepo;

    @Autowired
    CommentService commentService;
    @Autowired
    CommentReplyService commentReplyService;
    @Autowired
    LikesService likesService;
    @Autowired
    ProfileService profileService;
    @Autowired
    SocialSendToService socialSendToService;
    @Autowired
    SocialCategoryMasterService socialCategoryMasterService;
    @Autowired
    SocialCallRequestService socialCallRequestService;
    @Autowired
    SocialPostRetrievalRepository socialPostRetrievalRepository;

    private String LOCATION = null;
    public SocialResponse createSocial(final SocialRequest request) {

        var dto = request.toDto(snowflake);
        dto.setSocialId(snowflake.nextId());

        if (!CollectionUtils.isEmpty(dto.getSendTo())) {
            for (var sendToDto : dto.getSendTo()) {
                sendToDto.setSendToId(snowflake.nextId());
            }
        }
        if (!CollectionUtils.isEmpty(dto.getSocialFileList())) {
            for (var socialFileDto : dto.getSocialFileList()) {
                socialFileDto.setSocialFileId(snowflake.nextId());
            }
        }

        return SocialResponse.fromDto(socialRepo.createSocial(dto));
    }

    public SocialResponse updateSocial(final SocialRequest request) {

        var dto = request.toDto(snowflake);

        return SocialResponse.fromDto(socialRepo.updateSocial(dto));
    }

    public SocialResponse deleteSocialPost(final Long socialId) {


        return socialRepo.deleteSocialPost(socialId)
                .map(dto -> SocialResponse.fromDto(dto))
                .get();
    }

    public List<SocialResponse> getSocials() {

        return socialRepo.getAllSocials().stream()
                .map(dto -> SocialResponse.fromDto(dto))
                .collect(Collectors.toList());

    }

    public SocialResponse retrieveById(Long socialId) {

        return socialRepo.retrieveById(socialId)
                .map(dto -> SocialResponse.fromDto(dto))
                .get();
    }



    private static SocialCallRequestResponse findCallRequestByProfileId(Collection<SocialCallRequestResponse> listCallRequest, String profileId) {
        return listCallRequest.stream().filter(callRequestResponse-> profileId.equals(callRequestResponse.getRequesterProfileId()))
                .findFirst().orElse(null);
    }
    public List<SocialResponse> getPostsByCommunity(Long communityId) {

        return socialRepo.getPostsByCommunity(communityId).stream()
                .map(dto -> SocialResponse.fromDto(dto))
                .collect(Collectors.toList());

    }

    public List<SocialResponse> search(final String searchString) {

        return socialRepo.search(searchString).stream()
                .map(dto -> SocialResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
    public SocialPostResponse retrieveSocialDetailsById(Long socialId) {
        log.info("Social ID###:"+socialId);
        SocialPostResponse socialPostResponse = socialRepo.retrieveById(socialId)
                .map(dto -> SocialPostResponse.fromDto(dto))
                .get();
        return setSocialDetails(socialPostResponse, null);
    }
    public List<SocialPostResponse> retrievePostsByProfileId(String profileId) {
        long startTime = System.nanoTime();
        List<String> categoryFiltersToBeApplied = new ArrayList<>();
        //categoryFiltersToBeApplied.add("Emergency");
        //categoryFiltersToBeApplied.add("Employment");
        //categoryFiltersToBeApplied.add("Share");
        List<String> commulityIdFilters= new ArrayList<>();
        //commulityIdFilters.add("1234567890");
        //commulityIdFilters.add("1234567891");
        var socialPostResponses = socialPostRetrievalRepository
                .getSocialListBySearch(Long.valueOf(profileId), 1, 50, categoryFiltersToBeApplied, commulityIdFilters)
                .stream()
                .map(dao -> dao.toDto())
                .map(dto -> SocialPostResponse.fromDto(dto))
                .collect(Collectors.toList());

        /*List<String> socialIds = getSocials().stream().map(SocialResponse::getSocialId).collect(Collectors.toList());
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        for (String socialId : socialIds) {
            var socialPostResponse = setSocialDetails(Long.valueOf(socialId), profileId);
            socialPostResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(socialPostResponse.getProfileId())));
            var sendToList = socialPostResponse.getSendTo();
            socialPostResponse.setLocation(getLocation(sendToList));
            socialPostResponses.add(socialPostResponse);
        }*/
        for (SocialPostResponse socialPostResponse:  socialPostResponses) {
            setSocialDetails(socialPostResponse, profileId);
            socialPostResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(socialPostResponse.getProfileId())));
            var sendToList = socialPostResponse.getSendTo().stream().
                    filter(p -> p.getType().contains("location_")).collect(Collectors.toList());
            socialPostResponse.setLocation(getLocation(sendToList));
        }
        return socialPostResponses;
    }
    public List<SocialPostResponse> retrievePostsCreatedByProfile(String profileId) {
        long startTime = System.nanoTime();

        //List<String> socialIds = search("profileId:"+profileId).stream().map(SocialResponse).collect(Collectors.toList());
        List<SocialPostResponse> socialPostResponses = socialRepo.search("profileId:"+profileId).stream()
                .map(dto -> SocialPostResponse.fromDto(dto))
                .collect(Collectors.toList());
        log.info("social post responses:"+socialPostResponses);
        /*for (String socialId : socialIds) {
            var socialPostResponse = setSocialDetails(Long.valueOf(socialId), profileId);
            socialPostResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(socialPostResponse.getProfileId())));
            var sendToList = socialPostResponse.getSendTo();
            socialPostResponse.setLocation(getLocation(sendToList));
            socialPostResponses.add(socialPostResponse);
        }*/
        for (SocialPostResponse socialPostResponse:  socialPostResponses) {
            setSocialDetails(socialPostResponse, profileId);
            socialPostResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(socialPostResponse.getProfileId())));
            var sendToList = socialPostResponse.getSendTo().stream().
                    filter(p -> p.getType().contains("location_")).collect(Collectors.toList());
            socialPostResponse.setLocation(getLocation(sendToList));
            //socialPostResponses.add(socialPostResponse);
        }
        return socialPostResponses;
    }

    public List<SocialPostResponse> retrievePostsByCommunity(Long communityId, String profileId) {
        long startTime = System.nanoTime();

        List<SocialPostResponse> socialIds = socialRepo.retrieveByCommunity(communityId).stream()
                .map(dto -> SocialPostResponse.fromDto(dto))
                .collect(Collectors.toList());
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        /*for (Long socialId : socialIds) {
            var socialPostResponse = setSocialDetails(Long.valueOf(socialId), profileId);
            socialPostResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(socialPostResponse.getProfileId())));
            var sendToList = socialPostResponse.getSendTo();
            socialPostResponse.setLocation(getLocation(sendToList));
            socialPostResponses.add(socialPostResponse);
        }*/
        for (SocialPostResponse socialPostResponse:  socialPostResponses) {
            setSocialDetails(socialPostResponse, profileId);
            socialPostResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(socialPostResponse.getProfileId())));
            var sendToList = socialPostResponse.getSendTo().stream().
                    filter(p -> p.getType().contains("location_")).collect(Collectors.toList());
            socialPostResponse.setLocation(getLocation(sendToList));
        }
        return socialPostResponses;
    }
    private SocialPostResponse setSocialDetails(SocialPostResponse socialPostResponse, String profileId) {
        /*log.info("Social ID###:"+socialId+"$$$Profile ID ID###:"+profileId);
        SocialPostResponse socialPostResponse = socialRepo.retrieveById(socialId)
                .map(dto -> SocialPostResponse.fromDto(dto))
                .get();*/
        SocialCategoryMasterResponse categoryMasterResponse = socialCategoryMasterService.retrieveById(Long.valueOf(socialPostResponse.getCategoryId()));
        List<CommentResponse> commentsList = commentService.searchComments("socialId:" + socialPostResponse.getSocialId());
        List<LikesResponse> likesList = likesService.searchLikes("socialId:" + socialPostResponse.getSocialId());
        if(profileId != null ) {
            List<CommentReplyResponse> replyList = commentReplyService.searchReplies("socialId:" + socialPostResponse.getSocialId());
            if (socialPostResponse.getSocialCallRequestResponses() != null) {
                SocialCallRequestResponse socialCallRequestResponse = findCallRequestByProfileId(socialPostResponse.getSocialCallRequestResponses(), profileId);
                if (socialCallRequestResponse != null)
                    socialPostResponse.setCallRequestStatusOfLoginUser(socialCallRequestResponse.getStatus());
            }
            socialPostResponse.setCommentsCount(( commentsList != null ? commentsList.size() : 0 ) + (replyList != null ? replyList.size() : 0));
        } else{
            log.info("SOCIAL ID##############:"+socialPostResponse.getSocialId());
            var socialSendToResponses = socialPostResponse.getSendTo().stream().
                    filter(p -> p.getType().contains("location_")).collect(Collectors.toList());
            socialPostResponse.setLocation(getLocation(socialSendToResponses));
            MinimalProfileResponse minimalProfileResponse = new MinimalProfileResponse();
            minimalProfileResponse.setProfileId(socialPostResponse.getProfileId());
            socialPostResponse.setMinimalProfileResponse(minimalProfileResponse);
            socialPostResponse.getMinimalProfileResponse().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(socialPostResponse.getProfileId())));
            socialPostResponse.setComments(commentsList);
            socialPostResponse.setLikes(likesList);
        }
        socialPostResponse.setLikesCount( likesList == null ? 0 : likesList.size() );
        socialPostResponse.setCreatedTimeStr(Utility.customTimeFormat(socialPostResponse.getCreatedTime()));
        if(categoryMasterResponse != null) {
            socialPostResponse.setCategoryName(categoryMasterResponse.getCategory());
            socialPostResponse.setCategoryColorCode(categoryMasterResponse.getColorCode());
        }
        return socialPostResponse;
    }

    private String getLocation(List<SocialSendToResponse> socialSendToResponses){
        SocialSendToResponse response = null;
        response = socialSendToResponses.stream().filter(o -> !o.getValue().equals("all") && o.getType().equals("location_city")).findFirst()
                .orElse(socialSendToResponses.stream().filter(o -> !o.getValue().equals("all") && o.getType().equals("location_district")).findFirst()
                        .orElse(socialSendToResponses.stream().filter(o -> o.getType().equals("location_state")).findFirst().orElse(null)
                        ));
        if(response != null)
            return response.getValue();
        else
            return null;
    }
    //TODO:fine tune performance
    private Set<String> getSocialListBySearch(Long profileId) {
        Optional<PersonalResponse> personalResponse = profileService.retrievePersonalProfileById(profileId);
        StringBuilder interestSearchString = new StringBuilder();
        Set<String> uniqueSocialSet = new HashSet<>();
        if (!personalResponse.isEmpty()) {

            List<PersonalProfileInterestResponse> personalProfileInterestResponses = personalResponse.get().getPersonalProfile().getInterests();
            for (PersonalProfileInterestResponse personalProfileInterestResponse : personalProfileInterestResponses) {
                if (interestSearchString.length() != 0) {
                    interestSearchString.append(" OR ");
                } else {
                    interestSearchString.append(" type:")
                            .append("Interests")
                            .append(" AND ( ");
                }
                interestSearchString.append("( value:" + personalProfileInterestResponse.getInterest() + " )");

            }
            interestSearchString.append(" )");
            LOCATION = personalResponse.get().getPersonalProfile().getPresentAddress().getCityTownVillage();
            String desigSearchStr = "type:Designation AND ( value:" + personalResponse.get().getPersonalProfile().getDesignation() + " )";
            String fpbSearchStr = "type:Field_Profession_Business AND ( value:" + personalResponse.get().getPersonalProfile().getFieldProfessionBusiness() + " )";
            String locationSearchStr = "type:Location AND ( value:" + LOCATION + " )";
            //log.info("Search Interests String::::"+interestSearchString);
            List<String> searchStringList = new ArrayList<>();
            searchStringList.add(interestSearchString.toString());
            searchStringList.add(desigSearchStr);
            searchStringList.add(fpbSearchStr);
            searchStringList.add(locationSearchStr);
            List<String> uniqueSocialList = null;
            for (String searchStr : searchStringList) {
                List<String> socialList = socialSendToService.searchSent(searchStr).stream()
                        .map(SocialSendToResponse::getSocialId)
                        .distinct()
                        .collect(Collectors.toList());
                log.info("########unique Socials for " + searchStr + " ::::" + socialList);
                if (uniqueSocialList != null) {
                    Set<String> set = new HashSet<>(socialList);
                    uniqueSocialSet.retainAll(set);
                } else {
                    uniqueSocialSet = new HashSet<>(socialList);
                }
            }
        }
        return uniqueSocialSet;
    }

}
