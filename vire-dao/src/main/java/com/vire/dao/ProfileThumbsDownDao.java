package com.vire.dao;

import com.vire.dto.ProfileThumbsDownDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="profile_thumbsdown")
@Data
public class ProfileThumbsDownDao {

    @Id
    @Column(name = "profile_thumbsdown_id")
    private Long profileThumbsDownId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "thumbs_up_by", nullable = false)
    private Long thumbsDownBy;

    @Column(name = "thumbs_up_reason", nullable = false)
    private String reason;

    @Column(name = "thumbs_up_description", nullable = false)
    private String description;

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

    public ProfileThumbsDownDto toDto() {

        var dto = new ProfileThumbsDownDto();

        dto.setProfileThumbsDownId(this.getProfileThumbsDownId());
        
        dto.setProfileId(this.getProfileId());
        dto.setThumbsDownBy(this.getThumbsDownBy());
        dto.setReason(this.getReason());
        dto.setDescription(this.getDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileThumbsDownDao fromDto(final ProfileThumbsDownDto dto) {

        var profileThumbsDown = new ProfileThumbsDownDao();

        profileThumbsDown.setProfileThumbsDownId(dto.getProfileThumbsDownId());
        
        profileThumbsDown.setProfileId(dto.getProfileId());
        profileThumbsDown.setThumbsDownBy(dto.getThumbsDownBy());
        profileThumbsDown.setReason(dto.getReason());
        profileThumbsDown.setDescription(dto.getDescription());

        return profileThumbsDown;
    }
}
