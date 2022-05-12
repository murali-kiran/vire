package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ExperienceCommentDto {
    private Long experienceCommentId;
    private Long commentorProfileId;
    private Long experienceId;
    private String comment;
    public Long createdTime;
    public Long updatedTime;
}
