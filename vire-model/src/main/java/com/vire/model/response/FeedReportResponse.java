package com.vire.model.response;

import com.vire.dto.FeedReportDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedReportResponse {

    private String feedReportId;
    
    private Long feedId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;
    private Long createdTime;
    private Long updatedTime;

    public static FeedReportResponse fromDto(final FeedReportDto dto) {

        var feedReport = new FeedReportResponse();

        feedReport.setFeedReportId(String.valueOf(dto.getFeedReportId()));
        feedReport.setFeedId(dto.getFeedId());
        feedReport.setReporterId(dto.getReporterId());
        feedReport.setReportReason(dto.getReportReason());
        feedReport.setReportDescription(dto.getReportDescription());
        feedReport.setCreatedTime(dto.getCreatedTime());
        feedReport.setUpdatedTime(dto.getUpdatedTime());

        return feedReport;
    }
}
