package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityProfileFileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityProfileFileRequest {

    private String communityProfileFileId;
    
    private String fileId;

    public CommunityProfileFileDto toDto(Snowflake snowflake) {

        var dto = new CommunityProfileFileDto();

        if(snowflake == null) {
            dto.setCommunityProfileFileId(this.getCommunityProfileFileId() == null ? null : Long.valueOf(this.getCommunityProfileFileId()));
        }else {
            dto.setCommunityProfileFileId(snowflake.nextId());
        }
        
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));

        return dto;
    }

    public CommunityProfileFileDto toDto() {
        return toDto(null);
    }
}