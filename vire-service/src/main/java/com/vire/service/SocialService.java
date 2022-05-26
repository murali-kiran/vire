package com.vire.service;

import com.vire.model.request.SocialRequest;
import com.vire.model.response.*;
import com.vire.repository.FileRepository;
import com.vire.repository.SocialRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    public SocialResponse createSocial(final SocialRequest request) {

        var dto = request.toDto();
        dto.setSocialId(snowflake.nextId());

        if (!CollectionUtils.isEmpty(dto.getSendTo())) {
            for (var sendToDto : dto.getSendTo()) {
                sendToDto.setSendToId(snowflake.nextId());
            }
        }

        return SocialResponse.fromDto(socialRepo.createSocial(dto));
    }

    public SocialResponse updateSocial(final SocialRequest request) {

        var dto = request.toDto();

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

    public SocialPostResponse retrieveSocialDetailsById(Long socialId) {
        SocialPostResponse socialPostResponse = socialRepo.retrieveById(socialId)
                .map(dto -> SocialPostResponse.fromDto(dto))
                .get();
        List<CommentResponse> commentsList = commentService.searchComments("socialId:" + socialId);
        List<CommentReplyResponse> commentReplyList = commentReplyService.searchReplies("socialId:" + socialId);
        List<LikesResponse> likesList = likesService.searchLikes("socialId:" + socialId);
        socialPostResponse.setComments(commentsList);
        socialPostResponse.setCommentsReply(commentReplyList);
        socialPostResponse.setLikes(likesList);
        return socialPostResponse;
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

    public List<SocialPostResponse> retrievePostsByProfileId(Long profileId) {
        long startTime = System.nanoTime();
        Set<String> socialIds = getSocialListBySearch(profileId);
        long endTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (endTime - startTime) / 1_000_000_000;
        log.info("****************duration:" + elapsedTimeInSecond);
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        for (String socialId : socialIds) {
            socialPostResponses.add(retrieveSocialDetailsById(Long.valueOf(socialId)));
        }
        return socialPostResponses;
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

            String desigSearchStr = "type:Designation AND ( value:" + personalResponse.get().getPersonalProfile().getDesignation() + " )";
            String fpbSearchStr = "type:Field_Profession_Business AND ( value:" + personalResponse.get().getPersonalProfile().getFieldProfessionBusiness() + " )";
            String locationSearchStr = "type:Location AND ( value:" + personalResponse.get().getPersonalProfile().getPresentAddress().getCityTownVillage() + " )";
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
