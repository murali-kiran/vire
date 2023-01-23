package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ProfileReportDto {
    private Long profileReportId;
    private Long profileId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;
    public Long createdTime;
    public Long updatedTime;
}
