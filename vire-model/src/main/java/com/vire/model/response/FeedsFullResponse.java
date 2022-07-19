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
    private MinimalProfileResponse minimalProfileResponse;
    //private String fileId;
    private List<FeedsSendToResponse> feedsSendTo;
    private List<FeedFileResponse> feedFileList;
    private List<FeedCommentResponse> comments;
    private List<FeedLikesResponse> likes;
    private ParentFeedResponse parentFeedResponse;
    private String createdTimeStr;
    private String location;
    private Integer commentsCount;
    private Boolean loginUserLiked;
    private Integer shareCount;
    private Integer likesCount;
    private  Long createdTime;
    private  Long updatedTime;

    public static FeedsFullResponse fromDto(FeedsDto dto){
        var response = new FeedsFullResponse();
        response.setFeedId(dto.getFeedId().toString());
        response.setProfileId(dto.getProfileId().toString());
        response.setDescription(dto.getDescription());

        if(dto.getParentFeedId() != null) {
            ParentFeedResponse parentFeedResponse = new ParentFeedResponse();
            parentFeedResponse.setFeedId(String.valueOf(dto.getParentFeedId()));
            response.setParentFeedResponse(parentFeedResponse);
        }
        //response.setFileId(dto.getFileId().toString());
        if (dto.getFeedsSendTo() != null && !dto.getFeedsSendTo().isEmpty()) {
            response.setFeedsSendTo(dto.getFeedsSendTo()
                    .stream()
                    .map(sendToDto -> FeedsSendToResponse.fromDto(sendToDto))
                    .collect(Collectors.toList())
            );
        }
        if (dto.getFeedFileList() != null && !dto.getFeedFileList().isEmpty()) {
            response.setFeedFileList(dto.getFeedFileList()
                    .stream()
                    .map(fileDto -> FeedFileResponse.fromDto(fileDto))
                    .collect(Collectors.toList())
            );
        }
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());

        return response;
    }
}
