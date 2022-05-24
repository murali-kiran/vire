package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class FeedReportDto {
    private Long feedReportId;
    private Long feedId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;
    public Long createdTime;
    public Long updatedTime;
}
