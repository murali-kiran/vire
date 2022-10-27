package com.vire.dao;

import com.vire.dto.FeedNotificationDto;
import com.vire.dto.FeedNotificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="feed_notification")
@Data
public class FeedNotificationDao {

    @Id
    @Column(name = "feed_notification_id")
    private Long feedNotificationId;
    

    @Column(name = "feed_notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedNotificationType feedNotificationType;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;
    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "feed_notification_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private NotificationDao notification;

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

    public FeedNotificationDto toDto() {

        var dto = new FeedNotificationDto();

        dto.setFeedNotificationId(this.getFeedNotificationId());
        
        dto.setFeedNotificationType(this.getFeedNotificationType());
        dto.setProfileId(this.getProfileId());
        dto.setFeedId(this.getFeedId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedNotificationDao fromDto(final FeedNotificationDto dto) {

        var feedNotification = new FeedNotificationDao();

        feedNotification.setFeedNotificationId(dto.getFeedNotificationId());
        
        feedNotification.setFeedNotificationType(dto.getFeedNotificationType());
        feedNotification.setProfileId(dto.getProfileId());
        feedNotification.setFeedId(dto.getFeedId());
        return feedNotification;
    }
}
