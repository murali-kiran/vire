package com.vire.model.response;

import com.vire.dto.RequesterProfileSettingDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RequesterProfileSettingResponse {

    private String requesterProfileSettingId;
    
    private String settingType;
    private Boolean isEnabled;
    private Long profileId;
    private Long requesterProfileId;
    private String status;
    private Long createdTime;
    private Long updatedTime;

    public static RequesterProfileSettingResponse fromDto(final RequesterProfileSettingDto dto) {

        var requesterProfileSetting = new RequesterProfileSettingResponse();

        requesterProfileSetting.setRequesterProfileSettingId(String.valueOf(dto.getRequesterProfileSettingId()));
        requesterProfileSetting.setSettingType(dto.getSettingType());
        requesterProfileSetting.setIsEnabled(dto.getIsEnabled());
        requesterProfileSetting.setProfileId(dto.getProfileId());
        requesterProfileSetting.setRequesterProfileId(dto.getRequesterProfileId());
        requesterProfileSetting.setStatus(dto.getStatus());
        requesterProfileSetting.setCreatedTime(dto.getCreatedTime());
        requesterProfileSetting.setUpdatedTime(dto.getUpdatedTime());

        return requesterProfileSetting;
    }
}
