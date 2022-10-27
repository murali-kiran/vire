package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.SocialNotificationDto;
import com.vire.dto.SocialNotificationType;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialNotificationRequest {

    private String socialNotificationId;
    
    private SocialNotificationType socialNotificationType;
    private Long profileId;

    public SocialNotificationDto toDto(Snowflake snowflake) {

        var dto = new SocialNotificationDto();

        if(snowflake == null) {
            dto.setSocialNotificationId(this.getSocialNotificationId() == null ? null : Long.valueOf(this.getSocialNotificationId()));
        }else {
            dto.setSocialNotificationId(snowflake.nextId());
        }
        
        dto.setSocialNotificationType(this.getSocialNotificationType());
        dto.setProfileId(this.getProfileId());

        return dto;
    }

    public SocialNotificationDto toDto() {
        return toDto(null);
    }
}