package com.vire.model.response;

import com.vire.dto.FeedCommentDto;
import com.vire.utils.Utility;
import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentResponse {

    private String feedCommentId;
    private MinimalProfileResponse commenterProfile;
    private String commentorProfileId;
    private String feedId;
    private String comment;
    private List<FeedCommentReplyResponse> feedCommentReplyResponseList;
    private String commentedTime;
    private Long createdTime;
    private Long updatedTime;

    public static FeedCommentResponse fromDto(final FeedCommentDto dto) {

        var feedComment = new FeedCommentResponse();

        feedComment.setFeedCommentId(String.valueOf(dto.getFeedCommentId()));
        
        feedComment.setCommentorProfileId(String.valueOf(dto.getCommentorProfileId()));
        feedComment.setFeedId(String.valueOf(dto.getFeedId()));
        feedComment.setComment(dto.getComment());
        if (dto.getFeedCommentReplyDtoList() != null && !dto.getFeedCommentReplyDtoList().isEmpty()) {
            feedComment.setFeedCommentReplyResponseList(dto.getFeedCommentReplyDtoList()
                    .stream()
                    .map(child -> FeedCommentReplyResponse.fromDto(child))
                    .collect(Collectors.toList()));
            Collections.sort(feedComment.getFeedCommentReplyResponseList(), Comparator.comparing(FeedCommentReplyResponse::getUpdatedTime).reversed());
        }
        if (dto.getCommentorProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getCommentorProfileId()));
            feedComment.setCommenterProfile(minProfileRes);
        }
        feedComment.setCommentedTime(Utility.calculateTimeDiff(dto.getUpdatedTime()));
        feedComment.setCreatedTime(dto.getCreatedTime());
        feedComment.setUpdatedTime(dto.getUpdatedTime());

        return feedComment;
    }
}
