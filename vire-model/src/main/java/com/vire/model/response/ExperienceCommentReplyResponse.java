package com.vire.model.response;

import com.vire.dto.ExperienceCommentReplyDto;
import com.vire.utils.Utility;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceCommentReplyResponse {

    private String experienceCommentReplyId;
    private MinimalProfileResponse replierProfile;
    private String replierProfileId;
    private String experienceId;
    private String commentId;
    private String reply;
    private String repliedTime;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceCommentReplyResponse fromDto(final ExperienceCommentReplyDto dto) {

        var experienceCommentReply = new ExperienceCommentReplyResponse();

        experienceCommentReply.setExperienceCommentReplyId(String.valueOf(dto.getExperienceCommentReplyId()));
        experienceCommentReply.setReplierProfileId(String.valueOf(dto.getReplierProfileId()));
        experienceCommentReply.setExperienceId(String.valueOf(dto.getExperienceId()));
        experienceCommentReply.setCommentId(String.valueOf(dto.getCommentId()));
        experienceCommentReply.setReply(dto.getReply());
        if (dto.getReplierProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getReplierProfileId()));
            experienceCommentReply.setReplierProfile(minProfileRes);
        }
        experienceCommentReply.setRepliedTime(Utility.calculateTimeDiff(dto.getUpdatedTime()));
        experienceCommentReply.setCreatedTime(dto.getCreatedTime());
        experienceCommentReply.setUpdatedTime(dto.getUpdatedTime());

        return experienceCommentReply;
    }
}
