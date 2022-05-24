package com.vire.dao;

import com.vire.dto.ProfileThumbsUpDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="profile_thumbsup")
@Data
public class ProfileThumbsUpDao {

    @Id
    @Column(name = "profile_thumbsup_id")
    private Long profileThumbsUpId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "thumbs_up_by", nullable = false)
    private Long thumbsUpBy;

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

    public ProfileThumbsUpDto toDto() {

        var dto = new ProfileThumbsUpDto();

        dto.setProfileThumbsUpId(this.getProfileThumbsUpId());
        
        dto.setProfileId(this.getProfileId());
        dto.setThumbsUpBy(this.getThumbsUpBy());
        dto.setReason(this.getReason());
        dto.setDescription(this.getDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileThumbsUpDao fromDto(final ProfileThumbsUpDto dto) {

        var profileThumbsUp = new ProfileThumbsUpDao();

        profileThumbsUp.setProfileThumbsUpId(dto.getProfileThumbsUpId());
        
        profileThumbsUp.setProfileId(dto.getProfileId());
        profileThumbsUp.setThumbsUpBy(dto.getThumbsUpBy());
        profileThumbsUp.setReason(dto.getReason());
        profileThumbsUp.setDescription(dto.getDescription());

        return profileThumbsUp;
    }
}
