package com.vire.dto;

import lombok.Data;


@Data
public class ExperienceFileDto {
    private Long experienceFileId;
    private Long fileId;
    public Long createdTime;
    public Long updatedTime;
}
