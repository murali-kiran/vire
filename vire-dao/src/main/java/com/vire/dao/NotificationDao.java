package com.vire.dao;

import com.vire.dto.NotificationDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="t_notification")
@Data
public class NotificationDao {

    @Id
    @Column(name = "t_notification_id")
    private Long notificationId;
    

    @Column(name = "creator_profile_id", nullable = false)
    private Long creatorProfileId;

    @Column(name = "responder_profile_id", nullable = false)
    private Long responderProfileId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "post_type", nullable = false)
    private String postType;

    @Column(name = "respond_reason", nullable = false)
    private String respondReason;

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
        
        dto.setCreatorProfileId(this.getCreatorProfileId());
        dto.setResponderProfileId(this.getResponderProfileId());
        dto.setPostId(this.getPostId());
        dto.setPostType(this.getPostType());
        dto.setRespondReason(this.getRespondReason());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static NotificationDao fromDto(final NotificationDto dto) {

        var notification = new NotificationDao();

        notification.setNotificationId(dto.getNotificationId());
        
        notification.setCreatorProfileId(dto.getCreatorProfileId());
        notification.setResponderProfileId(dto.getResponderProfileId());
        notification.setPostId(dto.getPostId());
        notification.setPostType(dto.getPostType());
        notification.setRespondReason(dto.getRespondReason());

        return notification;
    }
}
