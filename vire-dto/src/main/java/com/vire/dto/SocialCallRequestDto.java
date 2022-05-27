package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class SocialCallRequestDto {
    private Long socialCallRequestId;
    private Long profileId;
    private Long requesterProfileId;
    private String status;
    public Long createdTime;
    public Long updatedTime;
}
