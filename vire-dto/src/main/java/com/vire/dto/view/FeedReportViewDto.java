package com.vire.dto.view;

import lombok.Data;


@Data
public class FeedReportViewDto {
    private Long feedReportId;
    private Long feedId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;
    public Long createdTime;
    public Long updatedTime;
}
