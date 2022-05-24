package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ProfileThumbsUpDto {
    private Long profileThumbsUpId;
    private Long profileId;
    private Long thumbsUpBy;
    private String reason;
    private String description;
    public Long createdTime;
    public Long updatedTime;
}
