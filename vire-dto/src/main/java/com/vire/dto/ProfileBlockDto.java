package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ProfileBlockDto {
    private Long profileBlockId;
    private Long profileId;
    private Long blockedProfileId;
    public Long createdTime;
    public Long updatedTime;
}
