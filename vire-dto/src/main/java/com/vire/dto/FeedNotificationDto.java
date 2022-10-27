package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class FeedNotificationDto {
    private Long feedNotificationId;
    private FeedNotificationType feedNotificationType;
    private Long profileId;
    private Long feedId;
    public Long createdTime;
    public Long updatedTime;
}
