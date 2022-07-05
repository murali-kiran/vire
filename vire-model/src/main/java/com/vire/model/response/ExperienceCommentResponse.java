package com.vire.model.response;

import com.vire.dto.ExperienceCommentDto;
import com.vire.utils.Utility;
import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceCommentResponse {

    private String experienceCommentId;
    private MinimalProfileResponse commentorProfile;
    private String commentorProfileId;
    private String experienceId;
    private String comment;
    private List<ExperienceCommentReplyResponse> experienceCommentReplyResponseList;
    private String commentedTime;
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
        if (dto.getCommentorProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getCommentorProfileId()));
            experienceComment.setCommentorProfile(minProfileRes);
        }
        experienceComment.setCommentedTime(Utility.calculateTimeDiff(dto.getUpdatedTime()));
        experienceComment.setCreatedTime(dto.getCreatedTime());
        experienceComment.setUpdatedTime(dto.getUpdatedTime());

        return experienceComment;
    }
}
