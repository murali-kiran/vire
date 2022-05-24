package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ChannelProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChannelProfileRequest {

    private String channelProfileId;
    
    private Long profileId;

    public ChannelProfileDto toDto(Snowflake snowflake) {

        var dto = new ChannelProfileDto();

        if(snowflake == null) {
            dto.setChannelProfileId(this.getChannelProfileId() == null ? null : Long.valueOf(this.getChannelProfileId()));
        }else {
            dto.setChannelProfileId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId());

        return dto;
    }

    public ChannelProfileDto toDto() {
        return toDto(null);
    }
}