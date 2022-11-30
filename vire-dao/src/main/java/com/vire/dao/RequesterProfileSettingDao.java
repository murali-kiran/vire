package com.vire.dao;

import com.vire.dto.RequesterProfileSettingDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="requester_profile_setting")
@Data
public class RequesterProfileSettingDao {

    @Id
    @Column(name = "requester_profile_setting_id")
    private Long requesterProfileSettingId;
    

    @Column(name = "setting_type", nullable = false)
    private String settingType;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "requester_profile_id", nullable = false)
    private Long requesterProfileId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public RequesterProfileSettingDto toDto() {

        var dto = new RequesterProfileSettingDto();

        dto.setRequesterProfileSettingId(this.getRequesterProfileSettingId());
        
        dto.setSettingType(this.getSettingType());
        dto.setIsEnabled(this.getIsEnabled());
        dto.setProfileId(this.getProfileId());
        dto.setRequesterProfileId(this.getRequesterProfileId());
        dto.setStatus(this.getStatus());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static RequesterProfileSettingDao fromDto(final RequesterProfileSettingDto dto) {

        var requesterProfileSetting = new RequesterProfileSettingDao();

        requesterProfileSetting.setRequesterProfileSettingId(dto.getRequesterProfileSettingId());
        
        requesterProfileSetting.setSettingType(dto.getSettingType());
        requesterProfileSetting.setIsEnabled(dto.getIsEnabled());
        requesterProfileSetting.setProfileId(dto.getProfileId());
        requesterProfileSetting.setRequesterProfileId(dto.getRequesterProfileId());
        requesterProfileSetting.setStatus(dto.getStatus());

        return requesterProfileSetting;
    }
}
