package com.vire.model.request;

import com.vire.dto.ExperienceFileDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class ExperienceFileRequest {

    private String experienceFileId;
    
    private String fileId;

    public ExperienceFileDto toDto(Snowflake snowflake) {

        var dto = new ExperienceFileDto();

        if(snowflake == null) {
            dto.setExperienceFileId(this.getExperienceFileId() == null ? null : Long.valueOf(this.getExperienceFileId()));
        }else {
            dto.setExperienceFileId(snowflake.nextId());
        }
        
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));

        return dto;
    }

    public ExperienceFileDto toDto() {
        return toDto(null);
    }
}