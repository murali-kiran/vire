package com.vire.dao;

import com.vire.dto.ExperienceDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="experience")
@Data
public class ExperienceDao {

    @Id
    @Column(name = "experience_id")
    private Long experienceId;
    

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;
    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceFileDao> experienceFileList;
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

    public ExperienceDto toDto() {

        var dto = new ExperienceDto();

        dto.setExperienceId(this.getExperienceId());
        
        dto.setCategoryId(this.getCategoryId());
        dto.setProfileId(this.getProfileId());
        dto.setFileId(this.getFileId());
        dto.setTitle(this.getTitle());
        dto.setDescription(this.getDescription());
        dto.setLocation(this.getLocation());
        if (this.getExperienceFileList() != null && !this.getExperienceFileList().isEmpty()) {
            dto.setExperienceFileList(this.getExperienceFileList()
                    .stream()
                    .map(child -> child.toDto())
                    .collect(Collectors.toList()));
        }
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ExperienceDao fromDto(final ExperienceDto dto) {

        var experience = new ExperienceDao();

        experience.setExperienceId(dto.getExperienceId());
        
        experience.setCategoryId(dto.getCategoryId());
        experience.setProfileId(dto.getProfileId());
        experience.setFileId(dto.getFileId());
        experience.setTitle(dto.getTitle());
        experience.setDescription(dto.getDescription());
        experience.setLocation(dto.getLocation());
        if (dto.getExperienceFileList() != null && !dto.getExperienceFileList().isEmpty()) {
            experience.setExperienceFileList(dto.getExperienceFileList()
                    .stream()
                    .map(child -> {
                        var childDao = ExperienceFileDao.fromDto(child);
                        childDao.setExperience(experience);
                        return childDao;
                    })                    .collect(Collectors.toList()));
        }
        return experience;
    }
}
