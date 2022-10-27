package com.vire.dao;

import com.vire.dto.NotificationDto;
import com.vire.dto.NotificationType;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="t_notification")
@Data
public class NotificationDao {

    @Id
    @Column(name = "t_notification_id")
    private Long notificationId;
    

    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "notifier_profile_id", nullable = false)
    private Long notifierProfileId;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @OneToOne(mappedBy = "notification", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CommunityNotificationDao communityNotification;

    @OneToOne(mappedBy = "notification", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SocialNotificationDao socialNotification;

    @OneToOne(mappedBy = "notification", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FeedNotificationDao feedNotification;

    @OneToOne(mappedBy = "notification", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProfileNotificationDao profileNotification;

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

    public NotificationDto toDto() {

        var dto = new NotificationDto();

        dto.setNotificationId(this.getNotificationId());
        
        dto.setNotificationType(this.getNotificationType());
        dto.setNotifierProfileId(this.getNotifierProfileId());
        dto.setIsRead(this.getIsRead());

        if (this.getCommunityNotification() != null) {
            dto.setCommunityNotification(this.getCommunityNotification().toDto());
        }


        if (this.getSocialNotification() != null) {
            dto.setSocialNotification(this.getSocialNotification().toDto());
        }


        if (this.getFeedNotification() != null) {
            dto.setFeedNotification(this.getFeedNotification().toDto());
        }


        if (this.getProfileNotification() != null) {
            dto.setProfileNotification(this.getProfileNotification().toDto());
        }


        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static NotificationDao fromDto(final NotificationDto dto) {

        var notification = new NotificationDao();

        notification.setNotificationId(dto.getNotificationId());
        
        notification.setNotificationType(dto.getNotificationType());
        notification.setNotifierProfileId(dto.getNotifierProfileId());
        notification.setIsRead(dto.getIsRead());

        if (dto.getCommunityNotification() != null) {
            notification.setCommunityNotification(CommunityNotificationDao.fromDto(dto.getCommunityNotification()));
            notification.getCommunityNotification().setNotification(notification);
        }


        if (dto.getSocialNotification() != null) {
            notification.setSocialNotification(SocialNotificationDao.fromDto(dto.getSocialNotification()));
            notification.getSocialNotification().setNotification(notification);
        }


        if (dto.getFeedNotification() != null) {
            notification.setFeedNotification(FeedNotificationDao.fromDto(dto.getFeedNotification()));
            notification.getFeedNotification().setNotification(notification);
        }


        if (dto.getProfileNotification() != null) {
            notification.setProfileNotification(ProfileNotificationDao.fromDto(dto.getProfileNotification()));
            notification.getProfileNotification().setNotification(notification);
        }


        return notification;
    }
}
