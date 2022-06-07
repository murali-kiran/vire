package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityFileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityFileRequest {

    private String communityFileId;
    
    private String fileId;

    public CommunityFileDto toDto(Snowflake snowflake) {

        var dto = new CommunityFileDto();

        if(snowflake == null) {
            dto.setCommunityFileId(this.getCommunityFileId() == null ? null : Long.valueOf(this.getCommunityFileId()));
        }else {
            dto.setCommunityFileId(snowflake.nextId());
        }
        
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));

        return dto;
    }

    public CommunityFileDto toDto() {
        return toDto(null);
    }
}