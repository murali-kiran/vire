package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ExperienceLikesDto {
    private Long experienceLikesId;
    private Long likerProfileId;
    private Long experienceId;
    public Long createdTime;
    public Long updatedTime;
}
