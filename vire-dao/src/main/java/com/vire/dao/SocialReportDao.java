package com.vire.dao;

import com.vire.dto.SocialReportDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="social_report")
@Data
public class SocialReportDao {

    @Id
    @Column(name = "social_report_id")
    private Long socialReportId;
    

    @Column(name = "social_id", nullable = false)
    private Long socialId;

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

    public SocialReportDto toDto() {

        var dto = new SocialReportDto();

        dto.setSocialReportId(this.getSocialReportId());
        
        dto.setSocialId(this.getSocialId());
        dto.setReporterId(this.getReporterId());
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialReportDao fromDto(final SocialReportDto dto) {

        var socialReport = new SocialReportDao();

        socialReport.setSocialReportId(dto.getSocialReportId());
        
        socialReport.setSocialId(dto.getSocialId());
        socialReport.setReporterId(dto.getReporterId());
        socialReport.setReportReason(dto.getReportReason());
        socialReport.setReportDescription(dto.getReportDescription());

        return socialReport;
    }
}
