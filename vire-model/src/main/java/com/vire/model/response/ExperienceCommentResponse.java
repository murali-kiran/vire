package com.vire.model.response;

import com.vire.dto.ExperienceCommentDto;
import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceCommentResponse {

    private String experienceCommentId;
    
    private String commentorProfileId;
    private String experienceId;
    private String comment;
    private List<ExperienceCommentReplyResponse> experienceCommentReplyResponseList;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceCommentResponse fromDto(final ExperienceCommentDto dto) {

        var experienceComment = new ExperienceCommentResponse();

        experienceComment.setExperienceCommentId(String.valueOf(dto.getExperienceCommentId()));
        experienceComment.setCommentorProfileId(String.valueOf(dto.getCommentorProfileId()));
        experienceComment.setExperienceId(String.valueOf(dto.getExperienceId()));
        experienceComment.setComment(dto.getComment());
        if (dto.getExperienceCommentReplyDtoList() != null && !dto.getExperienceCommentReplyDtoList().isEmpty()) {
            experienceComment.setExperienceCommentReplyResponseList(dto.getExperienceCommentReplyDtoList()
                    .stream()
                    .map(child -> ExperienceCommentReplyResponse.fromDto(child))
                    .collect(Collectors.toList()));
            Collections.sort(experienceComment.getExperienceCommentReplyResponseList(), Comparator.comparing(ExperienceCommentReplyResponse::getUpdatedTime).reversed());
        }
        experienceComment.setCreatedTime(dto.getCreatedTime());
        experienceComment.setUpdatedTime(dto.getUpdatedTime());

        return experienceComment;
    }
}
