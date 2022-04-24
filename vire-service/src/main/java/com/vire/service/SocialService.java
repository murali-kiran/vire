package com.vire.service;

import com.vire.model.request.PersonalProfileInterestRequest;
import com.vire.model.request.SocialRequest;
import com.vire.model.response.*;
import com.vire.repository.*;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        SocialPostResponse socialPostResponse =  socialRepo.retrieveById(socialId)
                .map(dto -> SocialPostResponse.fromDto(dto))
                .get();
        List<CommentResponse> commentsList = commentService.searchComments("socialId:"+socialId);
        List<CommentReplyResponse> commentReplyList = commentReplyService.searchReplies("socialId:"+socialId);
        List<LikesResponse> likesList = likesService.searchLikes("socialId:"+socialId);
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

        PersonalResponse personalResponse = profileService.retrievePersonalProfileById(profileId).get();
        StringBuilder searchString = new StringBuilder();
        if(personalResponse != null){
           List<PersonalProfileInterestRequest> personalProfileInterestRequests = personalResponse.getPersonalProfile().getInterests();

            for (PersonalProfileInterestRequest personalProfileInterestRequest : personalProfileInterestRequests) {
                if(searchString.length() != 0) {
                    searchString.append(" OR ");
                }else{
                    searchString.append(" type:")
                            .append("INTERESTS")
                            .append(" AND ( ");
                }
                searchString.append("( value:"+ personalProfileInterestRequest.getInterest()+" )");

            }
            searchString.append(" )");
           // interests.deleteCharAt(interests.length()-1);
        }
        System.out.println("Search String::::"+searchString.toString());

        String designation = personalResponse.getPersonalProfile().getDesignation();
        String fieldProfessionBusiness = personalResponse.getPersonalProfile().getFieldProfessionBusiness();
        String location = personalResponse.getPersonalProfile().getPresentAddress().getCityTownVillage();
        //searchString.append(" ( ")
        List<String> uniqueSocials = socialSendToService.searchSent(searchString.toString()).stream()
                .map(SocialSendToResponse::getSocialId)
                .distinct()
                .collect(Collectors.toList());
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        for (String socialId : uniqueSocials) {
            socialPostResponses.add(retrieveSocialDetailsById(Long.valueOf(socialId)));
        }
        return socialPostResponses;
    }
}
