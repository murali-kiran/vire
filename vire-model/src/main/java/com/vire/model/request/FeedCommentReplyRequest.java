package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedCommentReplyDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentReplyRequest {

    private String feedCommentReplyId;
    @NotBlank(message = "Replier Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Replier Profile id must be numeric")
    private Long replierProfileId;
    @NotBlank(message = "Feed id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Feed id must be numeric")
    private Long feedId;
    @NotBlank(message = "Comment id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Comment id must be numeric")
    private Long commentId;
    @NotBlank(message = "Reply required")
    private String reply;

    public FeedCommentReplyDto toDto(Snowflake snowflake) {

        var dto = new FeedCommentReplyDto();

        if(snowflake == null) {
            dto.setFeedCommentReplyId(this.getFeedCommentReplyId() == null ? null : Long.valueOf(this.getFeedCommentReplyId()));
        }else {
            dto.setFeedCommentReplyId(snowflake.nextId());
        }
        
        dto.setReplierProfileId(this.getReplierProfileId());
        dto.setFeedId(this.getFeedId());
        dto.setCommentId(this.getCommentId());
        dto.setReply(this.getReply());

        return dto;
    }

    public FeedCommentReplyDto toDto() {
        return toDto(null);
    }
}