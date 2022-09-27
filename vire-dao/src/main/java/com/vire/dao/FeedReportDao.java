package com.vire.dao;

import com.vire.dto.FeedReportDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="Feed_report")
@Data
public class FeedReportDao {

    @Id
    @Column(name = "feed_report_id")
    private Long feedReportId;
    

    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    @Column(name = "report_reason", nullable = false)
    private String reportReason;

    @Column(name = "report_description", nullable = false)
    private String reportDescription;

    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public FeedReportDto toDto() {

        var dto = new FeedReportDto();

        dto.setFeedReportId(this.getFeedReportId());
        
        dto.setFeedId(this.getFeedId());
        dto.setReporterId(this.getReporterId());
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedReportDao fromDto(final FeedReportDto dto) {

        var feedReport = new FeedReportDao();

        feedReport.setFeedReportId(dto.getFeedReportId());
        
        feedReport.setFeedId(dto.getFeedId());
        feedReport.setReporterId(dto.getReporterId());
        feedReport.setReportReason(dto.getReportReason());
        feedReport.setReportDescription(dto.getReportDescription());

        return feedReport;
    }
}
