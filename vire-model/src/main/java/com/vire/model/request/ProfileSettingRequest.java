package com.vire.model.request;

import com.vire.dto.ProfileSettingDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class ProfileSettingRequest {

    private String profileSettingId;
    
    private String settingType;
    private Boolean isEnable;

    public ProfileSettingDto toDto(Snowflake snowflake) {

        var dto = new ProfileSettingDto();

        if(snowflake == null) {
            dto.setProfileSettingId(this.getProfileSettingId() == null ? null : Long.valueOf(this.getProfileSettingId()));
        }else {
            dto.setProfileSettingId(snowflake.nextId());
        }
        
        dto.setSettingType(this.getSettingType());
        dto.setIsEnable(this.getIsEnable());

        return dto;
    }

    public ProfileSettingDto toDto() {
        return toDto(null);
    }
}