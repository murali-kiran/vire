package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedNotificationDto;
import com.vire.dto.FeedNotificationType;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedNotificationRequest {

    private String feedNotificationId;
    
    private FeedNotificationType feedNotificationType;
    private Long profileId;
    private Long feedId;
    public FeedNotificationDto toDto(Snowflake snowflake) {

        var dto = new FeedNotificationDto();

        if(snowflake == null) {
            dto.setFeedNotificationId(this.getFeedNotificationId() == null ? null : Long.valueOf(this.getFeedNotificationId()));
        }else {
            dto.setFeedNotificationId(snowflake.nextId());
        }
        
        dto.setFeedNotificationType(this.getFeedNotificationType());
        dto.setProfileId(this.getProfileId());
        dto.setFeedId(this.getFeedId());
        return dto;
    }

    public FeedNotificationDto toDto() {
        return toDto(null);
    }
}