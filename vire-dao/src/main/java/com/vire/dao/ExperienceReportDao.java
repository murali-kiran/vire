package com.vire.dao;

import com.vire.dto.ExperienceReportDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="experience_report")
@Data
public class ExperienceReportDao {

    @Id
    @Column(name = "experience_report_id")
    private Long experienceReportId;
    

    @Column(name = "experience_id", nullable = false)
    private Long experienceId;

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

    public ExperienceReportDto toDto() {

        var dto = new ExperienceReportDto();

        dto.setExperienceReportId(this.getExperienceReportId());
        
        dto.setExperienceId(this.getExperienceId());
        dto.setReporterId(this.getReporterId());
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ExperienceReportDao fromDto(final ExperienceReportDto dto) {

        var experienceReport = new ExperienceReportDao();

        experienceReport.setExperienceReportId(dto.getExperienceReportId());
        
        experienceReport.setExperienceId(dto.getExperienceId());
        experienceReport.setReporterId(dto.getReporterId());
        experienceReport.setReportReason(dto.getReportReason());
        experienceReport.setReportDescription(dto.getReportDescription());

        return experienceReport;
    }
}
