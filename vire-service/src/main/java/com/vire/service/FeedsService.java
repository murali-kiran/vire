package com.vire.service;

import com.vire.model.request.FeedsRequest;
import com.vire.model.response.*;
import com.vire.repository.FeedsRepository;
import com.vire.repository.FileRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FeedsService {

    @Autowired
    Snowflake snowflake;

    @Autowired
    FeedsRepository feedsRepo;

    @Autowired
    FileRepository feedsImageRepo;

    @Autowired
    FeedCommentService commentService;

    @Autowired
    FeedCommentReplyService commentReplyService;

    @Autowired
    FeedLikesService likesService;
    @Autowired
    ProfileService profileService;
    @Autowired
    FeedsSendToService feedsSendToService;

    @Autowired
    ChannelService channelService;
    @Autowired
    CommunityService communityService;

    public FeedsResponse createFeeds(final FeedsRequest request) {

        var dto = request.toDto(snowflake);
        dto.setFeedId(snowflake.nextId());

        if (!CollectionUtils.isEmpty(dto.getFeedsSendTo())) {
            for (var sendToDto : dto.getFeedsSendTo()) {
                sendToDto.setFeedsSendToId(snowflake.nextId());
            }
        }

        return FeedsResponse.fromDto(feedsRepo.createFeeds(dto));
    }

    public FeedsResponse updateFeeds(final FeedsRequest request) {

        var dto = request.toDto(snowflake);

        return FeedsResponse.fromDto(feedsRepo.updateFeeds(dto));
    }

    public FeedsResponse deleteFeedsPost(final Long feedsId) {

        return feedsRepo.deleteFeedsPost(feedsId)
                .map(dto -> FeedsResponse.fromDto(dto))
                .get();
    }

    public List<FeedsResponse> getFeeds() {

        return feedsRepo.getAllFeeds().stream()
                .map(dto -> FeedsResponse.fromDto(dto))
                .collect(Collectors.toList());

    }

    public FeedsResponse retrieveById(Long feedsId) {

        return feedsRepo.retrieveById(feedsId)
                .map(dto -> FeedsResponse.fromDto(dto))
                .get();
    }
    public FeedsFullResponse retrieveFeedsDetailsById(Long feedsId, String profileId) {
        FeedsFullResponse feedsFullResponse =  feedsRepo.retrieveById(feedsId)
                .map(dto -> FeedsFullResponse.fromDto(dto))
                .get();
        if(feedsFullResponse != null){

            return setFeedsDetailResponse(feedsFullResponse, profileId, false);
        }
        else{
            throw new RuntimeException("Record not found for id:"+feedsId);
        }
    }
    public List<FeedsFullResponse> retrieveFeedPostsByProfileId(String profileId) {
        List<String> feedsIdList = getAll().stream().map(FeedsResponse::getFeedId).collect(Collectors.toList());
        List<FeedsFullResponse> feedsFullResponseList = new ArrayList<>();
        for (String feedId : feedsIdList) {
            FeedsFullResponse feedsFullResponse = feedsRepo.retrieveById(Long.valueOf(feedId))
                    .map(dto -> FeedsFullResponse.fromDto(dto))
                    .get();
            feedsFullResponseList.add(setFeedsDetailResponse(feedsFullResponse, profileId, true));
        }
        return feedsFullResponseList;
    }
    public List<FeedsResponse> getAll() {

        return feedsRepo
                .getAllFeeds()
                .stream()
                .map(dto -> FeedsResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
    public List<FeedsResponse> search(final String searchString) {

        return feedsRepo.search(searchString).stream()
                .map(dto -> FeedsResponse.fromDto(dto))
                .collect(Collectors.toList());
    }

    private List<FeedsFullResponse> retrievePostsByProfileId(String profileId) {
        PersonalResponse personalResponse = profileService.retrievePersonalProfileById(Long.valueOf(profileId)).get();
        StringBuilder searchString = new StringBuilder();
        if(personalResponse != null){
           List<PersonalProfileInterestResponse> personalProfileInterestResponses = personalResponse.getPersonalProfile().getInterests();
            for (PersonalProfileInterestResponse personalProfileInterestResponse : personalProfileInterestResponses) {
                if(searchString.length() != 0) {
                    searchString.append(" OR ");
                }else{
                    searchString.append(" type:")
                            .append("INTERESTS")
                            .append(" AND ( ");
                }
                searchString.append("( value:"+ personalProfileInterestResponse.getInterest()+" )");
            }
            searchString.append(" )");
        }
        String designation = personalResponse.getPersonalProfile().getDesignation();
        String fieldProfessionBusiness = personalResponse.getPersonalProfile().getFieldProfessionBusiness();
        String location = personalResponse.getPersonalProfile().getPresentAddress().getCityTownVillage();
        searchString.append(" AND type:DESIGNATION AND ( value:"+designation+ " )");
        searchString.append(" AND type:FPB AND ( value:"+fieldProfessionBusiness+ " )");
        searchString.append(" AND type:LOCATION AND ( value:"+location+ " )");
        log.info("Search String::::"+searchString.toString());
        List<String> uniqueFeeds = feedsSendToService.searchSent(searchString.toString()).stream()
                .map(FeedsSendToResponse::getFeedId)
                .distinct()
                .collect(Collectors.toList());
        List<FeedsFullResponse> feedsResponses = new ArrayList<>();
        for (String feedsId : uniqueFeeds) {
            feedsResponses.add(retrieveFeedsDetailsById(Long.valueOf(feedsId), profileId));
        }
        return feedsResponses;
    }
    private FeedsFullResponse setFeedsDetailResponse(FeedsFullResponse feedsFullResponse, String profileId, boolean isList){
        List<FeedCommentResponse> commentsList = commentService.search("feedId:" + feedsFullResponse.getFeedId());
        List<FeedLikesResponse> likesList = likesService.search("feedId:" + feedsFullResponse.getFeedId());
        Integer replyCount = commentReplyService.countByFeedId(Long.valueOf(feedsFullResponse.getFeedId()));
        if(!isList) {
            feedsFullResponse.setComments(commentsList);
            feedsFullResponse.setLikes(likesList);
        }
        feedsFullResponse.setLikesCount( likesList == null ? 0 : likesList.size() );
        feedsFullResponse.setCommentsCount((commentsList != null ? commentsList.size() : 0) + (replyCount != null ? replyCount : 0));
        List<String> profileIds = feedsFullResponse.getLikes() == null ? null : feedsFullResponse.getLikes().stream().map(FeedLikesResponse::getLikerProfileId).collect(Collectors.toList());
        feedsFullResponse.setLoginUserLiked((profileIds != null && profileIds.contains(profileId)) ? true : false);
        DateFormat sdf2 = new SimpleDateFormat("MMMM dd 'at' HH:mm");
        sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        feedsFullResponse.setCreatedTimeStr(sdf2.format(new Date(feedsFullResponse.getCreatedTime())));
        if(feedsFullResponse.getFeedsSendTo() != null) {
            Optional<FeedsSendToResponse> feedsSendToResponse = feedsFullResponse.getFeedsSendTo().stream().
                    filter(p -> p.getType().equals("Location")).
                    findFirst();
            if(feedsSendToResponse != null && !feedsSendToResponse.isEmpty()) {
                feedsFullResponse.setLocation(feedsSendToResponse.get().getValue());
            }
            feedsFullResponse.getFeedsSendTo().stream()
                    .map(feedsSendTo -> setChannelCommunityName(feedsSendTo))
                    .collect(Collectors.toList());
        }
        MinimalProfileResponse minimalProfileResponse = new MinimalProfileResponse();
        minimalProfileResponse.setProfileId(feedsFullResponse.getProfileId());
        feedsFullResponse.setMinimalProfileResponse(minimalProfileResponse);
        feedsFullResponse.getMinimalProfileResponse().cloneProperties(
                profileService.retrieveProfileDtoById(
                        Long.valueOf(feedsFullResponse.getProfileId())));
        return feedsFullResponse;
    }

    private FeedsSendToResponse setChannelCommunityName(FeedsSendToResponse feedsSendToResponse){
        if(feedsSendToResponse.getType().equalsIgnoreCase("channel")){
            ChannelResponse response = channelService.retrieveById(Long.valueOf(feedsSendToResponse.getValue()));
            feedsSendToResponse.setName(response == null ? null : response.getName());
        } else if(feedsSendToResponse.getType().equalsIgnoreCase("community")){
            CommunityResponse response = communityService.retrieveById(Long.valueOf(feedsSendToResponse.getValue()));
            feedsSendToResponse.setName(response == null ? null : response.getName());
        }
        return feedsSendToResponse;
    }
    public List<KeyValueListResponse> getCommunityAndChannelByProfile(Long profileId) {
        List<KeyValueListResponse> keyValueChannelList = channelService.retrieveChannelsByProfileStatus(profileId, "Admin");
        List<KeyValueListResponse> keyValueCommunityList = communityService.retrieveCommunitiesByProfileStatus(profileId, "Admin,Accepted");
        return Stream.of(keyValueChannelList,keyValueCommunityList).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
