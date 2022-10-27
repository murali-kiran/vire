package com.vire.dao;

import com.vire.dto.ProfileNotificationDto;
import com.vire.dto.ProfileNotificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="profile_notification")
@Data
public class ProfileNotificationDao {

    @Id
    @Column(name = "profile_notification_id")
    private Long profileNotificationId;
    

    @Column(name = "profile_notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileNotificationType profileNotificationType;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "profile_notification_id")
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

    public ProfileNotificationDto toDto() {

        var dto = new ProfileNotificationDto();

        dto.setProfileNotificationId(this.getProfileNotificationId());
        
        dto.setProfileNotificationType(this.getProfileNotificationType());
        dto.setProfileId(this.getProfileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileNotificationDao fromDto(final ProfileNotificationDto dto) {

        var profileNotification = new ProfileNotificationDao();

        profileNotification.setProfileNotificationId(dto.getProfileNotificationId());
        
        profileNotification.setProfileNotificationType(dto.getProfileNotificationType());
        profileNotification.setProfileId(dto.getProfileId());

        return profileNotification;
    }
}
