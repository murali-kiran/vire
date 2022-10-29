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
    private String profileId;
    private String socialId;
    private Long createdTime;
    private Long updatedTime;

    public static SocialNotificationResponse fromDto(final SocialNotificationDto dto) {

        var socialNotification = new SocialNotificationResponse();

        socialNotification.setSocialNotificationId(String.valueOf(dto.getSocialNotificationId()));
        socialNotification.setSocialNotificationType(dto.getSocialNotificationType());
        socialNotification.setProfileId(String.valueOf(dto.getProfileId()));
        socialNotification.setSocialId(String.valueOf(dto.getSocialId()));
        socialNotification.setCreatedTime(dto.getCreatedTime());
        socialNotification.setUpdatedTime(dto.getUpdatedTime());

        return socialNotification;
    }
}
