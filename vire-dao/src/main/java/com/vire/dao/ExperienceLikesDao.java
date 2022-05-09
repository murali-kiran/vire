package com.vire.dao;

import com.vire.dto.ExperienceLikesDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="experience_likes")
@Data
public class ExperienceLikesDao {

    @Id
    @Column(name = "experience_likes_id")
    private Long experienceLikesId;
    

    @Column(name = "liker_profile_id", nullable = false)
    private Long likerProfileId;

    @Column(name = "experience_id", nullable = false)
    private Long experienceId;

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

    public ExperienceLikesDto toDto() {

        var dto = new ExperienceLikesDto();

        dto.setExperienceLikesId(this.getExperienceLikesId());
        
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setExperienceId(this.getExperienceId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ExperienceLikesDao fromDto(final ExperienceLikesDto dto) {

        var experienceLikes = new ExperienceLikesDao();

        experienceLikes.setExperienceLikesId(dto.getExperienceLikesId());
        
        experienceLikes.setLikerProfileId(dto.getLikerProfileId());
        experienceLikes.setExperienceId(dto.getExperienceId());

        return experienceLikes;
    }
}
