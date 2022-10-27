package com.vire.model.response;

import com.vire.dto.ProfileNotificationDto;
import com.vire.dto.ProfileNotificationType;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileNotificationResponse {

    private String profileNotificationId;
    
    private ProfileNotificationType profileNotificationType;
    private Long profileId;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileNotificationResponse fromDto(final ProfileNotificationDto dto) {

        var profileNotification = new ProfileNotificationResponse();

        profileNotification.setProfileNotificationId(String.valueOf(dto.getProfileNotificationId()));
        profileNotification.setProfileNotificationType(dto.getProfileNotificationType());
        profileNotification.setProfileId(dto.getProfileId());
        profileNotification.setCreatedTime(dto.getCreatedTime());
        profileNotification.setUpdatedTime(dto.getUpdatedTime());

        return profileNotification;
    }
}
