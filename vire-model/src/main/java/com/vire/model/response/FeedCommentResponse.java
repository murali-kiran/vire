package com.vire.model.response;

import com.vire.dto.FeedCommentDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentResponse {

    private String feedCommentId;
    
    private String commentorProfileId;
    private String feedId;
    private String comment;
    private Long createdTime;
    private Long updatedTime;

    public static FeedCommentResponse fromDto(final FeedCommentDto dto) {

        var feedComment = new FeedCommentResponse();

        feedComment.setFeedCommentId(String.valueOf(dto.getFeedCommentId()));
        
        feedComment.setCommentorProfileId(String.valueOf(dto.getCommentorProfileId()));
        feedComment.setFeedId(String.valueOf(dto.getFeedId()));
        feedComment.setComment(dto.getComment());
        feedComment.setCreatedTime(dto.getCreatedTime());
        feedComment.setUpdatedTime(dto.getUpdatedTime());

        return feedComment;
    }
}
