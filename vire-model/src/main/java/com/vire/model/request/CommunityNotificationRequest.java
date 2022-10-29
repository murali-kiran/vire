package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityNotificationDto;
import com.vire.dto.CommunityNotificationType;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityNotificationRequest {

    private String communityNotificationId;
    
    private CommunityNotificationType communityNotificationType;
    private Long profileId;
    private Long communityId;
    public CommunityNotificationDto toDto(Snowflake snowflake) {

        var dto = new CommunityNotificationDto();

        if(snowflake == null) {
            dto.setCommunityNotificationId(this.getCommunityNotificationId() == null ? null : Long.valueOf(this.getCommunityNotificationId()));
        }else {
            dto.setCommunityNotificationId(snowflake.nextId());
        }
        dto.setCommunityId(this.getCommunityId());
        dto.setCommunityNotificationType(this.getCommunityNotificationType());
        dto.setProfileId(this.getProfileId());

        return dto;
    }

    public CommunityNotificationDto toDto() {
        return toDto(null);
    }
}