package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ProfileNotificationDto;
import com.vire.dto.ProfileNotificationType;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileNotificationRequest {

    private String profileNotificationId;
    
    private ProfileNotificationType profileNotificationType;
    private String profileId;

    public ProfileNotificationDto toDto(Snowflake snowflake) {

        var dto = new ProfileNotificationDto();

        if(snowflake == null) {
            dto.setProfileNotificationId(this.getProfileNotificationId() == null ? null : Long.valueOf(this.getProfileNotificationId()));
        }else {
            dto.setProfileNotificationId(snowflake.nextId());
        }
        
        dto.setProfileNotificationType(this.getProfileNotificationType());
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));

        return dto;
    }

    public ProfileNotificationDto toDto() {
        return toDto(null);
    }
}