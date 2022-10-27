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
    private String locationState;
    private String locationDistrict;
    private String locationCity;
    private List<ExperienceFileDto> experienceFileList;
    private Long viewCount;
    private Long createdTime;
    private Long updatedTime;
    private Long deletedTime;
}
