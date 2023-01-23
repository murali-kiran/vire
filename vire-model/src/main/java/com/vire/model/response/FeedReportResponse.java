package com.vire.model.response;

import com.vire.dto.FeedReportDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedReportResponse {

    private String feedReportId;
    
    private String feedId;
    private String reporterId;
    private String reportReason;
    private String reportDescription;
    private Long createdTime;
    private Long updatedTime;

    public static FeedReportResponse fromDto(final FeedReportDto dto) {

        var feedReport = new FeedReportResponse();

        feedReport.setFeedReportId(String.valueOf(dto.getFeedReportId()));
        feedReport.setFeedId(String.valueOf(dto.getFeedId()));
        feedReport.setReporterId(String.valueOf(dto.getReporterId()));
        feedReport.setReportReason(dto.getReportReason());
        feedReport.setReportDescription(dto.getReportDescription());
        feedReport.setCreatedTime(dto.getCreatedTime());
        feedReport.setUpdatedTime(dto.getUpdatedTime());

        return feedReport;
    }
}
