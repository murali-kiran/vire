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
    private String creatorProfileId;
    private String fileId;
    private String coverFileId;
    private String rules;
    private List<ChannelProfileResponse> profiles;
    private Long createdTime;
    private Long updatedTime;

    public static ChannelResponse fromDto(final ChannelDto dto) {

        var channel = new ChannelResponse();

        channel.setChannelId(String.valueOf(dto.getChannelId()));
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        channel.setCreatorProfileId(dto.getCreatorProfileId() == null ? null : String.valueOf(dto.getCreatorProfileId()));
        channel.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));
        channel.setCoverFileId(dto.getCoverFileId() == null ? null : String.valueOf(dto.getCoverFileId()));
        channel.setRules(dto.getRules());

        if (dto.getProfiles() != null && !dto.getProfiles().isEmpty()) {
            channel.setProfiles(dto.getProfiles()
                    .stream()
                    .map(child -> ChannelProfileResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }

        channel.setCreatedTime(dto.getCreatedTime());
        channel.setUpdatedTime(dto.getUpdatedTime());

        return channel;
    }
}
