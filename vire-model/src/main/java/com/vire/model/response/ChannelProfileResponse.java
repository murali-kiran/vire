package com.vire.model.response;

import com.vire.dto.ChannelProfileDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChannelProfileResponse {

    private String channelProfileId;
    
    private Long profileId;
    private Long createdTime;
    private Long updatedTime;

    public static ChannelProfileResponse fromDto(final ChannelProfileDto dto) {

        var channelProfile = new ChannelProfileResponse();

        channelProfile.setChannelProfileId(String.valueOf(dto.getChannelProfileId()));
        channelProfile.setProfileId(dto.getProfileId());
        channelProfile.setCreatedTime(dto.getCreatedTime());
        channelProfile.setUpdatedTime(dto.getUpdatedTime());

        return channelProfile;
    }
}
