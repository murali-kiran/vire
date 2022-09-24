package com.vire.dao.view;

import com.vire.dto.view.FeedReportViewDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Feed_report")
@Data
public class FeedReportViewDao {

    @Id
    @Column(name = "feed_report_id")
    private Long feedReportId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feed_id", nullable = false)
    private FeedsViewDao feed;

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


    public FeedReportViewDto toDto() {

        var dto = new FeedReportViewDto();
        dto.setFeedReportId(this.getFeedReportId());
        dto.setFeedId(this.getFeed().getFeedId());
        dto.setReporterId(this.getReporterId());
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

}
