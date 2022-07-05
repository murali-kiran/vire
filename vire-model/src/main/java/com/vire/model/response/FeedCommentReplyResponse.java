package com.vire.model.response;

import com.vire.dto.FeedCommentReplyDto;
import com.vire.utils.Utility;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentReplyResponse {

    private String feedCommentReplyId;
    private MinimalProfileResponse replierProfile;
    private String replierProfileId;
    private String feedId;
    private String commentId;
    private String reply;
    private String repliedTime;
    private Long createdTime;
    private Long updatedTime;

    public static FeedCommentReplyResponse fromDto(final FeedCommentReplyDto dto) {

        var feedCommentReply = new FeedCommentReplyResponse();

        feedCommentReply.setFeedCommentReplyId(String.valueOf(dto.getFeedCommentReplyId()));
        
        feedCommentReply.setReplierProfileId(String.valueOf(dto.getReplierProfileId()));
        feedCommentReply.setFeedId(String.valueOf(dto.getFeedId()));
        feedCommentReply.setCommentId(String.valueOf(dto.getCommentId()));
        feedCommentReply.setReply(dto.getReply());
        if (dto.getReplierProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getReplierProfileId()));
            feedCommentReply.setReplierProfile(minProfileRes);
        }
        feedCommentReply.setRepliedTime(Utility.calculateTimeDiff(dto.getUpdatedTime()));
        feedCommentReply.setCreatedTime(dto.getCreatedTime());
        feedCommentReply.setUpdatedTime(dto.getUpdatedTime());

        return feedCommentReply;
    }
}
