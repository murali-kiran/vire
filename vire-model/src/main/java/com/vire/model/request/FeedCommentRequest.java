package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedCommentDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentRequest {

    private String feedCommentId;
    
    private Long commentorProfileId;
    private Long feedId;
    private String comment;

    public FeedCommentDto toDto(Snowflake snowflake) {

        var dto = new FeedCommentDto();

        if(snowflake == null) {
            dto.setFeedCommentId(this.getFeedCommentId() == null ? null : Long.valueOf(this.getFeedCommentId()));
        }else {
            dto.setFeedCommentId(snowflake.nextId());
        }
        
        dto.setCommentorProfileId(this.getCommentorProfileId());
        dto.setFeedId(this.getFeedId());
        dto.setComment(this.getComment());

        return dto;
    }

    public FeedCommentDto toDto() {
        return toDto(null);
    }
}