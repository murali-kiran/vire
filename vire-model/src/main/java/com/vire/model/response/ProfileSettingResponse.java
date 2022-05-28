package com.vire.model.response;

import com.vire.dto.ProfileSettingDto;
import lombok.Data;

@Data
public class ProfileSettingResponse {

    private String profileSettingId;
    private String settingType;
    private Boolean isEnable;


    public static ProfileSettingResponse fromDto(final ProfileSettingDto dto) {

        var profileSetting = new ProfileSettingResponse();

        profileSetting.setProfileSettingId(String.valueOf(dto.getProfileSettingId()));
        profileSetting.setSettingType(dto.getSettingType());
        profileSetting.setIsEnable(dto.getIsEnable());

        return profileSetting;
    }
}
