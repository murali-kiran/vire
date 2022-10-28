package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.NotificationDto;
import com.vire.dto.NotificationType;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class NotificationRequest {

    private String notificationId;
    
    private NotificationType notificationType;
    private String notifierProfileId;
    private Boolean isRead;
    private CommunityNotificationRequest communityNotification;
    private SocialNotificationRequest socialNotification;
    private FeedNotificationRequest feedNotification;
    private ProfileNotificationRequest profileNotification;
    private String message;

    public NotificationDto toDto(Snowflake snowflake) {

        var dto = new NotificationDto();

        if(snowflake == null) {
            dto.setNotificationId(this.getNotificationId() == null ? null : Long.valueOf(this.getNotificationId()));
        }else {
            dto.setNotificationId(snowflake.nextId());
        }
        dto.setMessage(this.getMessage());
        dto.setNotificationType(this.getNotificationType());
        dto.setNotifierProfileId(this.getNotifierProfileId() == null ? null : Long.valueOf(this.getNotifierProfileId()));
        dto.setIsRead(this.getIsRead());

        if (this.getCommunityNotification() != null) {
            dto.setCommunityNotification(this.getCommunityNotification().toDto());
            dto.getCommunityNotification().setCommunityNotificationId(dto.getNotificationId());
        }


        if (this.getSocialNotification() != null) {
            dto.setSocialNotification(this.getSocialNotification().toDto());
            dto.getSocialNotification().setSocialNotificationId(dto.getNotificationId());
        }


        if (this.getFeedNotification() != null) {
            dto.setFeedNotification(this.getFeedNotification().toDto());
            dto.getFeedNotification().setFeedNotificationId(dto.getNotificationId());
        }


        if (this.getProfileNotification() != null) {
            dto.setProfileNotification(this.getProfileNotification().toDto());
            dto.getProfileNotification().setProfileNotificationId(dto.getNotificationId());
        }


        return dto;
    }

    public NotificationDto toDto() {
        return toDto(null);
    }
}