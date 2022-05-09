package com.vire.model.response;

import com.vire.dto.ExperienceCommentDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceCommentResponse {

    private String experienceCommentId;
    
    private String commentorProfileId;
    private String experienceId;
    private String comment;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceCommentResponse fromDto(final ExperienceCommentDto dto) {

        var experienceComment = new ExperienceCommentResponse();

        experienceComment.setExperienceCommentId(String.valueOf(dto.getExperienceCommentId()));
        experienceComment.setCommentorProfileId(String.valueOf(dto.getCommentorProfileId()));
        experienceComment.setExperienceId(String.valueOf(dto.getExperienceId()));
        experienceComment.setComment(dto.getComment());
        experienceComment.setCreatedTime(dto.getCreatedTime());
        experienceComment.setUpdatedTime(dto.getUpdatedTime());

        return experienceComment;
    }
}
