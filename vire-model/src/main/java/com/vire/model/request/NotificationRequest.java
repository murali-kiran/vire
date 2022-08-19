package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.NotificationDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class NotificationRequest {

    private String notificationId;
    
    private Long creatorProfileId;
    private Long responderProfileId;
    private Long postId;
    private String postType;
    private String respondReason;

    public NotificationDto toDto(Snowflake snowflake) {

        var dto = new NotificationDto();

        if(snowflake == null) {
            dto.setNotificationId(this.getNotificationId() == null ? null : Long.valueOf(this.getNotificationId()));
        }else {
            dto.setNotificationId(snowflake.nextId());
        }
        
        dto.setCreatorProfileId(this.getCreatorProfileId());
        dto.setResponderProfileId(this.getResponderProfileId());
        dto.setPostId(this.getPostId());
        dto.setPostType(this.getPostType());
        dto.setRespondReason(this.getRespondReason());

        return dto;
    }

    public NotificationDto toDto() {
        return toDto(null);
    }
}