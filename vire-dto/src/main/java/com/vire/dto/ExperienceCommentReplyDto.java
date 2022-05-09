package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ExperienceCommentReplyDto {
    private Long experienceCommentReplyId;
    private Long replierProfileId;
    private Long experienceId;
    private Long commentId;
    private String reply;
    public Long createdTime;
    public Long updatedTime;
}
