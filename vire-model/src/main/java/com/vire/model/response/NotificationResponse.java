package com.vire.model.response;

import com.vire.dto.NotificationDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class NotificationResponse {

    private String notificationId;
    
    private Long creatorProfileId;
    private Long responderProfileId;
    private Long postId;
    private String postType;
    private String respondReason;
    private Long createdTime;
    private Long updatedTime;

    public static NotificationResponse fromDto(final NotificationDto dto) {

        var notification = new NotificationResponse();

        notification.setNotificationId(String.valueOf(dto.getNotificationId()));
        notification.setCreatorProfileId(dto.getCreatorProfileId());
        notification.setResponderProfileId(dto.getResponderProfileId());
        notification.setPostId(dto.getPostId());
        notification.setPostType(dto.getPostType());
        notification.setRespondReason(dto.getRespondReason());
        notification.setCreatedTime(dto.getCreatedTime());
        notification.setUpdatedTime(dto.getUpdatedTime());

        return notification;
    }
}
