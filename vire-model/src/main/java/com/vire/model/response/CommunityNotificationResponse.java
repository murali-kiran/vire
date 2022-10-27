package com.vire.model.response;

import com.vire.dto.CommunityNotificationDto;
import com.vire.dto.CommunityNotificationType;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityNotificationResponse {

    private String communityNotificationId;
    
    private CommunityNotificationType communityNotificationType;
    private Long profileId;
    private Long createdTime;
    private Long updatedTime;

    public static CommunityNotificationResponse fromDto(final CommunityNotificationDto dto) {

        var communityNotification = new CommunityNotificationResponse();

        communityNotification.setCommunityNotificationId(String.valueOf(dto.getCommunityNotificationId()));
        communityNotification.setCommunityNotificationType(dto.getCommunityNotificationType());
        communityNotification.setProfileId(dto.getProfileId());
        communityNotification.setCreatedTime(dto.getCreatedTime());
        communityNotification.setUpdatedTime(dto.getUpdatedTime());

        return communityNotification;
    }
}
