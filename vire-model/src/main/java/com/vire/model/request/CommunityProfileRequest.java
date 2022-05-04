package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityProfileRequest {

    private String communityProfileId;
    
    private String profileId;

    public CommunityProfileDto toDto(Snowflake snowflake) {

        var dto = new CommunityProfileDto();

        if(snowflake == null) {
            dto.setCommunityProfileId(this.getCommunityProfileId() == null ? null : Long.valueOf(this.getCommunityProfileId()));
        }else {
            dto.setCommunityProfileId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));

        return dto;
    }

    public CommunityProfileDto toDto() {
        return toDto(null);
    }
}