package com.vire.dao;

import com.vire.dto.ProfileSettingDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="profile_settings")
@Data
public class ProfileSettingDao {

    @Id
    @Column(name = "profile_settings_id")
    private Long profileSettingId;
    

    @Column(name = "setting_type", nullable = false)
    private String settingType;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnable;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable=false)
    private ProfileDao profile;



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

    public ProfileSettingDto toDto() {

        var dto = new ProfileSettingDto();

        dto.setProfileSettingId(this.getProfileSettingId());
        
        dto.setSettingType(this.getSettingType());
        dto.setIsEnable(this.getIsEnable());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileSettingDao fromDto(final ProfileSettingDto dto) {

        var profileSetting = new ProfileSettingDao();

        profileSetting.setProfileSettingId(dto.getProfileSettingId());
        
        profileSetting.setSettingType(dto.getSettingType());
        profileSetting.setIsEnable(dto.getIsEnable());

        return profileSetting;
    }
}
