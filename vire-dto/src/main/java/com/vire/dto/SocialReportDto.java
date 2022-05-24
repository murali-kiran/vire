package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class SocialReportDto {
    private Long socialReportId;
    private Long socialId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;
    public Long createdTime;
    public Long updatedTime;
}
