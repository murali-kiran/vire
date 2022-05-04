package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CommunityProfileDto {
    private Long communityProfileId;
    private Long profileId;
    public Long createdTime;
    public Long updatedTime;
}
