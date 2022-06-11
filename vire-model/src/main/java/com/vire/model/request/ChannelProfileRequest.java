package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ChannelProfileDto;
import lombok.Data;

@Data
public class ChannelProfileRequest {

    private String channelProfileId;
    private String channelId;
    private String profileId;
    private String status;

    public ChannelProfileDto toDto(Snowflake snowflake) {

        var dto = new ChannelProfileDto();

        if(snowflake == null) {
            dto.setChannelProfileId(this.getChannelProfileId() == null ? null : Long.valueOf(this.getChannelProfileId()));
        }else {
            dto.setChannelProfileId(snowflake.nextId());
        }
        dto.setStatus(this.getStatus());
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));

        return dto;
    }

    public ChannelProfileDto toDto() {
        return toDto(null);
    }
}