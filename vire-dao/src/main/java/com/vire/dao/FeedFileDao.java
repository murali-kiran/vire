package com.vire.dao;

import com.vire.dto.FeedFileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="feed_files")
@Data
public class FeedFileDao {

    @Id
    @Column(name = "feed_file_id")
    private Long feedFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne
    @JoinColumn(name="feed_id", nullable=false)
    private FeedsDao feed;



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

    public FeedFileDto toDto() {

        var dto = new FeedFileDto();

        dto.setFeedFileId(this.getFeedFileId());
        
        dto.setFileId(this.getFileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedFileDao fromDto(final FeedFileDto dto) {

        var feedFile = new FeedFileDao();

        feedFile.setFeedFileId(dto.getFeedFileId());
        
        feedFile.setFileId(dto.getFileId());

        return feedFile;
    }
}
