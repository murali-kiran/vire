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
    private Long socialId;
    private Long socialMiscInfoId;
    private String miscType;

    public SocialNotificationDto toDto(Snowflake snowflake) {

        var dto = new SocialNotificationDto();

        if(snowflake == null) {
            dto.setSocialNotificationId(this.getSocialNotificationId() == null ? null : Long.valueOf(this.getSocialNotificationId()));
        }else {
            dto.setSocialNotificationId(snowflake.nextId());
        }
        dto.setSocialId(this.getSocialId());
        dto.setSocialNotificationType(this.getSocialNotificationType());
        dto.setProfileId(this.getProfileId());
        dto.setSocialMiscInfoId(this.getSocialMiscInfoId());
        dto.setMiscType(this.getMiscType());

        return dto;
    }

    public SocialNotificationDto toDto() {
        return toDto(null);
    }
}