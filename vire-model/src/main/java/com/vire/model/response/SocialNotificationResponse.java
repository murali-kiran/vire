package com.vire.model.response;

import com.vire.dto.SocialNotificationDto;
import com.vire.dto.SocialNotificationType;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialNotificationResponse {

    private String socialNotificationId;
    
    private SocialNotificationType socialNotificationType;
    private Long profileId;
    private Long createdTime;
    private Long updatedTime;

    public static SocialNotificationResponse fromDto(final SocialNotificationDto dto) {

        var socialNotification = new SocialNotificationResponse();

        socialNotification.setSocialNotificationId(String.valueOf(dto.getSocialNotificationId()));
        socialNotification.setSocialNotificationType(dto.getSocialNotificationType());
        socialNotification.setProfileId(dto.getProfileId());
        socialNotification.setCreatedTime(dto.getCreatedTime());
        socialNotification.setUpdatedTime(dto.getUpdatedTime());

        return socialNotification;
    }
}
