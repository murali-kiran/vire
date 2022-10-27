package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ProfileNotificationDto {
    private Long profileNotificationId;
    private ProfileNotificationType profileNotificationType;
    private Long profileId;
    public Long createdTime;
    public Long updatedTime;
}
