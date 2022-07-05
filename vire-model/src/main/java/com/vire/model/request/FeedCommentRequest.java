package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedCommentDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedCommentRequest {

    private String feedCommentId;
    @NotBlank(message = "Commenter Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Commenter Profile id must be numeric")
    private String commentorProfileId;
    @NotBlank(message = "Feed id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Feed id must be numeric")
    private String feedId;
    @NotBlank(message = "Comment required")
    private String comment;

    public FeedCommentDto toDto(Snowflake snowflake) {

        var dto = new FeedCommentDto();

        if(snowflake == null) {
            dto.setFeedCommentId(this.getFeedCommentId() == null ? null : Long.valueOf(this.getFeedCommentId()));
        }else {
            dto.setFeedCommentId(snowflake.nextId());
        }
        
        dto.setCommentorProfileId(this.getCommentorProfileId() == null ? null : Long.valueOf(this.getCommentorProfileId()));
        dto.setFeedId(this.getFeedId() == null ? null : Long.valueOf(this.getFeedId()));
        dto.setComment(this.getComment());

        return dto;
    }

    public FeedCommentDto toDto() {
        return toDto(null);
    }
}