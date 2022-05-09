package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ExperienceCommentReplyDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceCommentReplyRequest {

    private String experienceCommentReplyId;
    @NotBlank(message = "Replier Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Replier Profile id must be numeric")
    private String replierProfileId;
    @NotBlank(message = "Experience id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Experience id must be numeric")
    private String experienceId;
    @NotBlank(message = "Comment id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Comment id must be numeric")
    private String commentId;
    @NotBlank(message = "Reply required")
    private String reply;

    public ExperienceCommentReplyDto toDto(Snowflake snowflake) {

        var dto = new ExperienceCommentReplyDto();

        if(snowflake == null) {
            dto.setExperienceCommentReplyId(this.getExperienceCommentReplyId() == null ? null : Long.valueOf(this.getExperienceCommentReplyId()));
        }else {
            dto.setExperienceCommentReplyId(snowflake.nextId());
        }
        
        dto.setReplierProfileId(this.getReplierProfileId() == null ? null : Long.valueOf(this.getReplierProfileId()));
        dto.setExperienceId(this.getExperienceId() == null ? null : Long.valueOf(this.getExperienceId()));
        dto.setCommentId(this.getCommentId() == null ? null : Long.valueOf(this.getCommentId()));
        dto.setReply(this.getReply());

        return dto;
    }

    public ExperienceCommentReplyDto toDto() {
        return toDto(null);
    }
}