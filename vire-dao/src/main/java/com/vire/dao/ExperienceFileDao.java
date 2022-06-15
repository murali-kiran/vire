package com.vire.dao;

import com.vire.dto.ExperienceFileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="experience_files")
@Data
public class ExperienceFileDao {

    @Id
    @Column(name = "experience_file_id")
    private Long experienceFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne
    @JoinColumn(name="experience_id", nullable=false)
    private ExperienceDao experience;



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

    public ExperienceFileDto toDto() {

        var dto = new ExperienceFileDto();

        dto.setExperienceFileId(this.getExperienceFileId());
        
        dto.setFileId(this.getFileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ExperienceFileDao fromDto(final ExperienceFileDto dto) {

        var experienceFile = new ExperienceFileDao();

        experienceFile.setExperienceFileId(dto.getExperienceFileId());
        
        experienceFile.setFileId(dto.getFileId());

        return experienceFile;
    }
}
