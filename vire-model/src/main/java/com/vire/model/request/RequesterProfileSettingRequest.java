package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.RequesterProfileSettingDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RequesterProfileSettingRequest {

    private String requesterProfileSettingId;
    
    private String settingType;
    private Boolean isEnabled;
    private String profileId;
    private String requesterProfileId;
    private String status;

    public RequesterProfileSettingDto toDto(Snowflake snowflake) {

        var dto = new RequesterProfileSettingDto();

        if(snowflake == null) {
            dto.setRequesterProfileSettingId(this.getRequesterProfileSettingId() == null ? null : Long.valueOf(this.getRequesterProfileSettingId()));
        }else {
            dto.setRequesterProfileSettingId(this.getRequesterProfileSettingId() == null ? snowflake.nextId() : Long.valueOf(this.getRequesterProfileSettingId()));
        }
        
        dto.setSettingType(this.getSettingType());
        dto.setIsEnabled(this.getIsEnabled());
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setRequesterProfileId(this.getRequesterProfileId()  == null ? null : Long.valueOf(this.getRequesterProfileId()));
        dto.setStatus(this.getStatus());

        return dto;
    }

    public RequesterProfileSettingDto toDto() {
        return toDto(null);
    }
}