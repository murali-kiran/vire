package com.vire.model.response;


import com.vire.dto.FeedsDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedsFullResponse {

    private String feedId;
    private String profileId;
    private String description;
    private  MinimalProfileResponse minimalProfileResponse;
    //private String fileId;
    private List<FeedsSendToResponse> feedsSendTo;
    private List<FeedFileResponse> feedFileList;
    private List<FeedCommentResponse> comments;
    private List<FeedLikesResponse> likes;
    private String createdTimeStr;
    private String location;
    private  Long createdTime;
    private  Long updatedTime;

    public static FeedsFullResponse fromFeedResponse(FeedsResponse feedResponse){
        var response = new FeedsFullResponse();
        response.setFeedId(feedResponse.getFeedId().toString());
        response.setProfileId(feedResponse.getProfileId().toString());
        response.setDescription(feedResponse.getDescription());
        //response.setFileId(dto.getFileId().toString());
        response.setCreatedTime(feedResponse.getCreatedTime());
        response.setUpdatedTime(feedResponse.getUpdatedTime());
        response.setFeedsSendTo(feedResponse.getFeedsSendTo());
        response.setFeedFileList(feedResponse.getFeedFileList());

        return response;
    }
}
