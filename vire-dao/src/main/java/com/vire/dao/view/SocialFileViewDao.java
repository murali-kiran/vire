package com.vire.dao.view;

import com.vire.dao.SocialDao;
import com.vire.dto.SocialFileDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="social_file")
@Data
public class SocialFileViewDao {

    @Id
    @Column(name = "social_file_id")
    private Long socialFileId;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "social_id")
    private Long socialId;

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
