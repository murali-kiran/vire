package com.vire.model.response;

import com.vire.dto.ChannelProfileDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChannelProfileResponse {

    private String channelProfileId;
    
    private String profileId;
    private MinimalProfileResponse profile;
    private String channelId;
    private String status;
    private Long createdTime;
    private Long updatedTime;

    public static ChannelProfileResponse fromDto(final ChannelProfileDto dto) {

        var channelProfile = new ChannelProfileResponse();
        if (dto.getProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getProfileId()));
            channelProfile.setProfile(minProfileRes);
        }
        channelProfile.setChannelProfileId(dto.getChannelProfileId() == null ? null : String.valueOf(dto.getChannelProfileId()));
        channelProfile.setProfileId(dto.getProfileId() == null ? null : String.valueOf(dto.getProfileId()));
        channelProfile.setChannelId(dto.getChannelId() == null ? null : String.valueOf(dto.getChannelId()));
        channelProfile.setStatus(dto.getStatus());
        channelProfile.setCreatedTime(dto.getCreatedTime());
        channelProfile.setUpdatedTime(dto.getUpdatedTime());

        return channelProfile;
    }
}
