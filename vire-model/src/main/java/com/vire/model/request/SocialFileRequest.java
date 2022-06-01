package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.SocialFileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialFileRequest {

    private String socialFileId;
    
    private String fileId;

    public SocialFileDto toDto(Snowflake snowflake) {

        var dto = new SocialFileDto();

        if(snowflake == null) {
            dto.setSocialFileId(this.getSocialFileId() == null ? null : Long.valueOf(this.getSocialFileId()));
        }else {
            dto.setSocialFileId(snowflake.nextId());
        }
        
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));

        return dto;
    }

    public SocialFileDto toDto() {
        return toDto(null);
    }
}