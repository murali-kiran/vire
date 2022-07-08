package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ChannelDto {
    private Long channelId;
    private String name;
    private String description;
    private Long creatorProfileId;
    private String loginProfileId;
    private Long fileId;
    private Long coverFileId;
    private String rules;
    private List<ChannelProfileDto> profiles;
    private Boolean isPrivate;
    public Long createdTime;
    public Long updatedTime;
}
