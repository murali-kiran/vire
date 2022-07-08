package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ExperienceDto {
    private Long experienceId;
    private Long categoryId;
    private Long profileId;
    private Long fileId;
    private String title;
    private String description;
    private String location;
    private List<ExperienceFileDto> experienceFileList;
    private Long viewCount;
    public Long createdTime;
    public Long updatedTime;
}
