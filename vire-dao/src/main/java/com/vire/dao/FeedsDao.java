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

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedsSendToDao> feedsSendTo;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedFileDao> feedFileList;

    @Column(name = "parent_feed_id")
    private Long parentFeedId;

    @Column(name = "send_to_followers", nullable = false)
    private Boolean sendToFollowers;

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
                ", parentFeedId=" + parentFeedId +
                ", sendToFollowers=" + sendToFollowers +
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
        dto.setParentFeedId(this.getParentFeedId());
        dto.setSendToFollowers(this.getSendToFollowers());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        if (this.getFeedsSendTo() != null && !this.getFeedsSendTo().isEmpty()) {
            dto.setFeedsSendTo(this.getFeedsSendTo()
                    .stream()
                    .map(sendTo -> sendTo.toDto())
                    .collect(Collectors.toList())
            );
        }
        if (this.getFeedFileList() != null && !this.getFeedFileList().isEmpty()) {
            dto.setFeedFileList(this.getFeedFileList()
                    .stream()
                    .map(child -> child.toDto())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static FeedsDao fromDto(final FeedsDto dto) {
        var dao = new FeedsDao();

        dao.setFeedId(dto.getFeedId());
        dao.setProfileId(dto.getProfileId());
        dao.setDescription(dto.getDescription());
        dao.setFileId(dto.getFileId());
        dao.setParentFeedId(dto.getParentFeedId());
        dao.setSendToFollowers(dto.getSendToFollowers());
        dao.setCreatedTime(dto.getCreatedTime());
        dao.setUpdatedTime(dto.getUpdatedTime());

        if (dto.getFeedsSendTo() != null && !dto.getFeedsSendTo().isEmpty()) {
            dao.setFeedsSendTo(dto.getFeedsSendTo()
                    .stream()
                    .map(sendTo -> FeedsSendToDao.fromDto(sendTo))
                    .collect(Collectors.toList())
            );
        }
        if (dto.getFeedFileList() != null && !dto.getFeedFileList().isEmpty()) {
            dao.setFeedFileList(dto.getFeedFileList()
                    .stream()
                    .map(child -> {
                        var childDao = FeedFileDao.fromDto(child);
                        childDao.setFeed(dao);
                        return childDao;
                    })                    .collect(Collectors.toList()));
        }
        return dao;
    }
}