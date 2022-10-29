package com.vire.dao;

import com.vire.dto.SocialNotificationDto;
import com.vire.dto.SocialNotificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="social_notification")
@Data
public class SocialNotificationDao {

    @Id
    @Column(name = "social_notification_id")
    private Long socialNotificationId;
    

    @Column(name = "social_notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialNotificationType socialNotificationType;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "social_id", nullable = false)
    private Long socialId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "social_notification_id")
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

    public SocialNotificationDto toDto() {

        var dto = new SocialNotificationDto();

        dto.setSocialNotificationId(this.getSocialNotificationId());
        
        dto.setSocialNotificationType(this.getSocialNotificationType());
        dto.setProfileId(this.getProfileId());
        dto.setSocialId(this.getSocialId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialNotificationDao fromDto(final SocialNotificationDto dto) {

        var socialNotification = new SocialNotificationDao();

        socialNotification.setSocialNotificationId(dto.getSocialNotificationId());
        socialNotification.setSocialId(dto.getSocialId());
        socialNotification.setSocialNotificationType(dto.getSocialNotificationType());
        socialNotification.setProfileId(dto.getProfileId());

        return socialNotification;
    }
}
