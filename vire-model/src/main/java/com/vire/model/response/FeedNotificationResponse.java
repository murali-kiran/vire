package com.vire.model.response;

import com.vire.dto.FeedNotificationDto;
import com.vire.dto.FeedNotificationType;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedNotificationResponse {

    private String feedNotificationId;
    
    private FeedNotificationType feedNotificationType;
   // private MinimalProfileResponse responderProfile;
    private String profileId;
    private String feedId;
    private Long createdTime;
    private Long updatedTime;

    public static FeedNotificationResponse fromDto(final FeedNotificationDto dto) {

        var feedNotification = new FeedNotificationResponse();

        feedNotification.setFeedNotificationId(String.valueOf(dto.getFeedNotificationId()));
        feedNotification.setFeedNotificationType(dto.getFeedNotificationType());
        feedNotification.setProfileId(String.valueOf(dto.getProfileId()));
        feedNotification.setFeedId(String.valueOf(dto.getFeedId()));
        feedNotification.setCreatedTime(dto.getCreatedTime());
        feedNotification.setUpdatedTime(dto.getUpdatedTime());

        return feedNotification;
    }
}
