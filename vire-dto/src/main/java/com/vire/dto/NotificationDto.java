package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class NotificationDto {
    private Long notificationId;
    private NotificationType notificationType;
    private Long notifierProfileId;
    private Boolean isRead;
    private CommunityNotificationDto communityNotification;
    private SocialNotificationDto socialNotification;
    private FeedNotificationDto feedNotification;
    private ProfileNotificationDto profileNotification;
    private String message;
    public Long createdTime;
    public Long updatedTime;
    public Long deletedTime;
}
