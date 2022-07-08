package com.vire.model.response;

import com.vire.dto.ChannelDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChannelResponse {

    private String channelId;
    
    private String name;
    private String description;
    private String loginProfileId;
    private MinimalProfileResponse creatorProfile;
    private String fileId;
    private String coverFileId;
    private String rules;
    private List<ChannelProfileResponse> channelProfiles;
    private int acceptedUserCount;
    private String loginProfileChannelStatus;
    private Boolean isPrivate;
    private Long createdTime;
    private Long updatedTime;

    public static ChannelResponse fromDto(final ChannelDto dto) {

        var channel = new ChannelResponse();

        channel.setChannelId(String.valueOf(dto.getChannelId()));
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        //channel.setCreatorProfileId(dto.getCreatorProfileId() == null ? null : String.valueOf(dto.getCreatorProfileId()));
        if (dto.getCreatorProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getCreatorProfileId()));
            channel.setCreatorProfile(minProfileRes);
        }
        channel.setLoginProfileId(dto.getLoginProfileId());
        channel.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));
        channel.setCoverFileId(dto.getCoverFileId() == null ? null : String.valueOf(dto.getCoverFileId()));
        channel.setRules(dto.getRules());
        channel.setIsPrivate(dto.getIsPrivate());

        if (dto.getProfiles() != null && !dto.getProfiles().isEmpty()) {
            channel.setChannelProfiles(dto.getProfiles()
                    .stream()
                    .map(child -> ChannelProfileResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }

        channel.setCreatedTime(dto.getCreatedTime());
        channel.setUpdatedTime(dto.getUpdatedTime());

        return channel;
    }
}
