package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedCommentReplyDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentReplyRequest {

    private String feedCommentReplyId;
    
    private Long replierProfileId;
    private Long feedId;
    private Long commentId;
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