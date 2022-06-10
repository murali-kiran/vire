package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ChannelProfileDto {
    private Long channelProfileId;
    private Long profileId;
    private String status;
    public Long createdTime;
    public Long updatedTime;
}
