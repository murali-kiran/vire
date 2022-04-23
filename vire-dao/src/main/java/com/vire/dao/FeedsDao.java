package com.vire.dao;

import com.vire.dto.FeedsDto;
import com.vire.dto.FeedsDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_feeds")
@Data
public class FeedsDao {
    @Id
    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "description", length = 191)
    private String description;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<FeedsSendToDao> feedsSendTo;

    @Column(name = "created_time", nullable = false)
    private Long createdTime;

    @Column(name = "updated_time", nullable = false)
    private Long updatedTime;
    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @Override
    public String toString() {
        return "FeedsDao{" +
                "feedId=" + feedId +
                ", profileId=" + profileId +
                ", description='" + description + '\'' +
                ", fileId=" + fileId +
                ", feedsSendTo=" + feedsSendTo +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

    public FeedsDto toDto() {
        var dto = new FeedsDto();
        dto.setFeedId(this.getFeedId());
        dto.setProfileId(this.getProfileId());
        dto.setDescription(this.getDescription());
        dto.setFileId(this.getFileId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        if (this.getFeedsSendTo() != null && !this.getFeedsSendTo().isEmpty()) {
            dto.setFeedsSendTo(this.getFeedsSendTo()
                    .stream()
                    .map(sendTo -> sendTo.toDto())
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static FeedsDao fromDto(final FeedsDto dto) {
        var dao = new FeedsDao();

        dao.setFeedId(dto.getFeedId());
        dao.setProfileId(dto.getProfileId());
        dao.setDescription(dto.getDescription());
        dao.setFileId(dto.getFileId());
        dao.setCreatedTime(dto.getCreatedTime());
        dao.setUpdatedTime(dto.getUpdatedTime());

        if (dto.getFeedsSendTo() != null && !dto.getFeedsSendTo().isEmpty()) {
            dao.setFeedsSendTo(dto.getFeedsSendTo()
                    .stream()
                    .map(sendTo -> FeedsSendToDao.fromDto(sendTo))
                    .collect(Collectors.toList())
            );
        }
        return dao;
    }
}