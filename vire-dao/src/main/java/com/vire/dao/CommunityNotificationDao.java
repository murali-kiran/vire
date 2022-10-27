package com.vire.dao;

import com.vire.dto.CommunityNotificationDto;
import com.vire.dto.CommunityNotificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="community_notification")
@Data
public class CommunityNotificationDao {

    @Id
    @Column(name = "community_notification_id")
    private Long communityNotificationId;
    

    @Column(name = "community_notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunityNotificationType communityNotificationType;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "community_notification_id")
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

    public CommunityNotificationDto toDto() {

        var dto = new CommunityNotificationDto();

        dto.setCommunityNotificationId(this.getCommunityNotificationId());
        
        dto.setCommunityNotificationType(this.getCommunityNotificationType());
        dto.setProfileId(this.getProfileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static CommunityNotificationDao fromDto(final CommunityNotificationDto dto) {

        var communityNotification = new CommunityNotificationDao();

        communityNotification.setCommunityNotificationId(dto.getCommunityNotificationId());
        
        communityNotification.setCommunityNotificationType(dto.getCommunityNotificationType());
        communityNotification.setProfileId(dto.getProfileId());

        return communityNotification;
    }
}
