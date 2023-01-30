package com.vire.dao;

import com.vire.dto.ProfileBlockDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="t_profile_block")
@Data
public class ProfileBlockDao {

    @Id
    @Column(name = "t_profile_block_id")
    private Long profileBlockId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "blocked_profile_id", nullable = false)
    private Long blockedProfileId;

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

    public ProfileBlockDto toDto() {

        var dto = new ProfileBlockDto();

        dto.setProfileBlockId(this.getProfileBlockId());
        
        dto.setProfileId(this.getProfileId());
        dto.setBlockedProfileId(this.getBlockedProfileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileBlockDao fromDto(final ProfileBlockDto dto) {

        var profileBlock = new ProfileBlockDao();

        profileBlock.setProfileBlockId(dto.getProfileBlockId());
        
        profileBlock.setProfileId(dto.getProfileId());
        profileBlock.setBlockedProfileId(dto.getBlockedProfileId());

        return profileBlock;
    }
}
