package com.vire.model.response;

import com.vire.dto.ExperienceFileDto;
import lombok.Data;

@Data
public class ExperienceFileResponse {

    private String experienceFileId;

    private String fileId;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceFileResponse fromDto(final ExperienceFileDto dto) {

        var experienceFile = new ExperienceFileResponse();

        experienceFile.setExperienceFileId(String.valueOf(dto.getExperienceFileId()));
        experienceFile.setFileId(String.valueOf(dto.getFileId()));
        experienceFile.setCreatedTime(dto.getCreatedTime());
        experienceFile.setUpdatedTime(dto.getUpdatedTime());

        return experienceFile;
    }
}
