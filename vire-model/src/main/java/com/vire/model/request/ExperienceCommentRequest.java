package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ExperienceCommentDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceCommentRequest {

    private String experienceCommentId;
    @NotBlank(message = "Commenter id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Commenter Profile id must be numeric")
    private String commentorProfileId;
    @NotBlank(message = "Experience id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Experience id must be numeric")
    private String experienceId;
    @NotBlank(message = "Comment Required")
    private String comment;

    public ExperienceCommentDto toDto(Snowflake snowflake) {

        var dto = new ExperienceCommentDto();

        if(snowflake == null) {
            dto.setExperienceCommentId(this.getExperienceCommentId() == null ? null : Long.valueOf(this.getExperienceCommentId()));
        }else {
            dto.setExperienceCommentId(snowflake.nextId());
        }
        
        dto.setCommentorProfileId(this.getCommentorProfileId() == null ? null : Long.valueOf(this.getCommentorProfileId()));
        dto.setExperienceId(this.getExperienceId() == null ? null : Long.valueOf(this.getExperienceId()));
        dto.setComment(this.getComment());

        return dto;
    }

    public ExperienceCommentDto toDto() {
        return toDto(null);
    }
}