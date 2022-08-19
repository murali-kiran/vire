package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class NotificationDto {
    private Long notificationId;
    private Long creatorProfileId;
    private Long responderProfileId;
    private Long postId;
    private String postType;
    private String respondReason;
    public Long createdTime;
    public Long updatedTime;
}
