package com.vire.dao.view;

import com.vire.dao.ExperienceDao;
import com.vire.dto.ExperienceFileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="experience_files")
@Data
public class ExperienceFileViewDao {

    @Id
    @Column(name = "experience_file_id")
    private Long experienceFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name="experience_id", nullable=false)
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


}
