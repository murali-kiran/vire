package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.NotificationDto;
import com.vire.dto.NotificationType;
import com.vire.utils.Utility;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {

    private String notificationId;

    private NotificationType notificationType;
    private String notifierProfileId;
    private Boolean isRead;
    private CommunityNotificationResponse communityNotification;
    private SocialNotificationResponse socialNotification;
    private FeedNotificationResponse feedNotification;
    private ProfileNotificationResponse profileNotification;
    private String createdTimeStr;
    private String message;
    private MinimalProfileResponse responderProfile;
    private Long createdTime;
    private Long updatedTime;

    public static NotificationResponse fromDto(final NotificationDto dto) {

        var notification = new NotificationResponse();

        notification.setNotificationId(String.valueOf(dto.getNotificationId()));
        notification.setNotificationType(dto.getNotificationType());
        notification.setNotifierProfileId(String.valueOf(dto.getNotifierProfileId()));
        notification.setIsRead(dto.getIsRead());
        notification.setMessage(dto.getMessage());
        notification.setCreatedTimeStr(Utility.customTimeFormat(dto.getCreatedTime()));
        if (dto.getCommunityNotification() != null) {
            notification.setCommunityNotification(CommunityNotificationResponse.fromDto(dto.getCommunityNotification()));
        }


        if (dto.getSocialNotification() != null) {
            notification.setSocialNotification(SocialNotificationResponse.fromDto(dto.getSocialNotification()));
        }


        if (dto.getFeedNotification() != null) {
            notification.setFeedNotification(FeedNotificationResponse.fromDto(dto.getFeedNotification()));
        }


        if (dto.getProfileNotification() != null) {
            notification.setProfileNotification(ProfileNotificationResponse.fromDto(dto.getProfileNotification()));
        }

        notification.setCreatedTime(dto.getCreatedTime());
        notification.setUpdatedTime(dto.getUpdatedTime());

        return notification;
    }
}
