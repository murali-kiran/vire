package com.vire.model.response;

import lombok.Data;

@Data
public class NotificationCountResponse {
    Long appCount;
    Long socialChatCount;
    Long communityCount;
}
