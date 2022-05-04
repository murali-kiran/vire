package com.vire.dao;

import com.vire.dto.CommunityProfileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="community_profile")
@Data
public class CommunityProfileDao {

    @Id
    @Column(name = "community_profile_id")
    private Long communityProfileId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name="community_id", nullable=false)
    private Long communityId;

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

    public CommunityProfileDto toDto() {

        var dto = new CommunityProfileDto();

        dto.setCommunityProfileId(this.getCommunityProfileId());
        
        dto.setProfileId(this.getProfileId());
        dto.setCommunityId(this.getCommunityId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static CommunityProfileDao fromDto(final CommunityProfileDto dto) {

        var communityProfile = new CommunityProfileDao();

        communityProfile.setCommunityProfileId(dto.getCommunityProfileId());
        communityProfile.setCommunityId(dto.getCommunityId());
        communityProfile.setProfileId(dto.getProfileId());

        return communityProfile;
    }
}
