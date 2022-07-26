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
    @Autowired
    FeedsRepository feedRepository;

    public FeedsResponse createFeeds(final FeedsRequest request) {
        /*if(request.getParentFeedId() != null ){
            FeedsResponse parentFeed = retrieveById(Long.valueOf(request.getParentFeedId()));
            if(request.getFeedFileList() == null && parentFeed != null){
                List<FeedFileRequest> feedFileRequests = new ArrayList<>();
                for (var feedFile : parentFeed.getFeedFileList()) {
                    FeedFileRequest feedFileRequest = new FeedFileRequest();
                    feedFileRequest.setFileId(feedFile.getFileId());
                    feedFileRequests.add(feedFileRequest);
                }
                request.setFeedFileList(feedFileRequests);
            }
        }*/
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
    public List<FeedsFullResponse> retrieveFeedsCreatedById(String profileId) {
        List<String> feedsIdList = search("profileId:"+profileId).stream().map(FeedsResponse::getFeedId).collect(Collectors.toList());
        List<FeedsFullResponse> feedsFullResponseList = new ArrayList<>();
        for (String feedId : feedsIdList) {
            FeedsFullResponse feedsFullResponse = feedsRepo.retrieveById(Long.valueOf(feedId))
                    .map(dto -> FeedsFullResponse.fromDto(dto))
                    .get();
            feedsFullResponseList.add(setFeedsDetailResponse(feedsFullResponse, profileId, true));
        }
        return feedsFullResponseList;
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

    public List<FeedsFullResponse> retrieveFeedPostsByCommunityId(Long communityId, String profileId) {
        Set<Long> feedsIdList = feedsRepo.retrieveByCommunity(communityId);
        List<FeedsFullResponse> feedsFullResponseList = new ArrayList<>();
        for (Long feedId : feedsIdList) {
            FeedsFullResponse feedsFullResponse = feedsRepo.retrieveById(feedId)
                    .map(dto -> FeedsFullResponse.fromDto(dto))
                    .get();
            feedsFullResponseList.add(setFeedsDetailResponse(feedsFullResponse, profileId, true));
        }
        return feedsFullResponseList;
    }

    public List<FeedsFullResponse> retrieveFeedPostsByChannelId(Long channelId, String profileId) {
        Set<Long> feedsIdList = feedsRepo.retrieveByChannel(channelId);
        List<FeedsFullResponse> feedsFullResponseList = new ArrayList<>();
        for (Long feedId : feedsIdList) {
            FeedsFullResponse feedsFullResponse = feedsRepo.retrieveById(feedId)
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
        feedsFullResponse.setShareCount(feedsRepo.countByParentFeedId(Long.valueOf(feedsFullResponse.getFeedId())));
        if(!isList) {
            feedsFullResponse.setComments(commentsList);
            feedsFullResponse.setLikes(likesList);
        }
        feedsFullResponse.setLikesCount( likesList == null ? 0 : likesList.size() );
        feedsFullResponse.setCommentsCount((commentsList != null ? commentsList.size() : 0) + (replyCount != null ? replyCount : 0));
        List<String> profileIds = likesList == null ? null : likesList.stream().map(FeedLikesResponse::getLikerProfileId).collect(Collectors.toList());
        feedsFullResponse.setLoginUserLiked((profileIds != null && profileIds.contains(profileId)) ? true : false);
        feedsFullResponse.setCreatedTimeStr(setDateFormat(feedsFullResponse.getCreatedTime()));
        feedsFullResponse.setLocation(setLocation(feedsFullResponse.getFeedsSendTo()));
        MinimalProfileResponse feedCreatorProfile = setMinimalProfile(feedsFullResponse.getProfileId());
        feedsFullResponse.setMinimalProfileResponse(feedCreatorProfile);
        if(feedsFullResponse.getParentFeedResponse() != null){
            feedsFullResponse.setParentFeedResponse(setParentFeedResponse(feedsFullResponse.getParentFeedResponse()));
        }
        return feedsFullResponse;
    }

    private String setLocation(List<FeedsSendToResponse> feedsSendToResponses){
        if(feedsSendToResponses != null) {
            Optional<FeedsSendToResponse> feedsSendToResponse = feedsSendToResponses.stream().
                    filter(p -> p.getType().equals("Location")).
                    findFirst();
            if(feedsSendToResponse != null && !feedsSendToResponse.isEmpty()) {
                return feedsSendToResponse.get().getValue();
            }
        }
        return null;
    }
    private String setDateFormat(Long createdTime){
        DateFormat sdf2 = new SimpleDateFormat("MMMM dd 'at' HH:mm");
        sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdf2.format(new Date(createdTime));
    }
    private ParentFeedResponse setParentFeedResponse(ParentFeedResponse parentFeedResponse){
        FeedsResponse feedsResponse = retrieveById(Long.valueOf(parentFeedResponse.getFeedId()));
        if(feedsResponse != null) {
            MinimalProfileResponse parentFeedCreatorProfile = setMinimalProfile(feedsResponse.getProfileId());
            parentFeedResponse.setMinimalProfileResponse(parentFeedCreatorProfile);
            parentFeedResponse.setDescription(feedsResponse.getDescription());
            parentFeedResponse.setLocation(setLocation(feedsResponse.getFeedsSendTo()));
            parentFeedResponse.setFeedFileList(feedsResponse.getFeedFileList());
            parentFeedResponse.setCreatedTimeStr(setDateFormat(feedsResponse.getCreatedTime()));
            parentFeedResponse.setFeedsSendTo(feedsResponse.getFeedsSendTo());
            return parentFeedResponse;
        }
        return null;
    }
    private MinimalProfileResponse setMinimalProfile(String profileId){
        MinimalProfileResponse minimalProfileResponse = new MinimalProfileResponse();
        minimalProfileResponse.setProfileId(profileId);
        minimalProfileResponse.cloneProperties(profileService.retrieveProfileDtoById(Long.valueOf(profileId)));
        return minimalProfileResponse;
    }
    /*private FeedsSendToResponse setChannelCommunityName(FeedsSendToResponse feedsSendToResponse){
        if(feedsSendToResponse.getType().equalsIgnoreCase("channel")){
            ChannelResponse response = channelService.retrieveById(Long.valueOf(feedsSendToResponse.getValue()));
            feedsSendToResponse.setName(response == null ? null : response.getName());
        } else if(feedsSendToResponse.getType().equalsIgnoreCase("community")){
            CommunityResponse response = communityService.retrieveById(Long.valueOf(feedsSendToResponse.getValue()));
            feedsSendToResponse.setName(response == null ? null : response.getName());
        }
        return feedsSendToResponse;
    }*/
    public List<KeyValueListResponse> getCommunityAndChannelByProfile(Long profileId) {
        List<KeyValueListResponse> keyValueChannelList = channelService.retrieveChannelsByProfileStatus(profileId, "Admin");
        List<KeyValueListResponse> keyValueCommunityList = communityService.retrieveCommunitiesByProfileStatus(profileId, "Admin,Accepted");
        return Stream.of(keyValueChannelList,keyValueCommunityList).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
