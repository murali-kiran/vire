package com.vire.dao.view;

import com.vire.dao.FeedsDao;
import com.vire.dto.FeedFileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="feed_files")
@Data
public class FeedFileViewDao {

    @Id
    @Column(name = "feed_file_id")
    private Long feedFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name="feed_id", nullable=false)
    private Long feedId;

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
