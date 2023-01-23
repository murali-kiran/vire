package com.vire.dao;

import com.vire.dto.ProfileReportDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="t_profile_report")
@Data
public class ProfileReportDao {

    @Id
    @Column(name = "t_profile_report_id")
    private Long profileReportId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

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

    public ProfileReportDto toDto() {

        var dto = new ProfileReportDto();

        dto.setProfileReportId(this.getProfileReportId());
        
        dto.setProfileId(this.getProfileId());
        dto.setReporterId(this.getReporterId());
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileReportDao fromDto(final ProfileReportDto dto) {

        var profileReport = new ProfileReportDao();

        profileReport.setProfileReportId(dto.getProfileReportId());
        
        profileReport.setProfileId(dto.getProfileId());
        profileReport.setReporterId(dto.getReporterId());
        profileReport.setReportReason(dto.getReportReason());
        profileReport.setReportDescription(dto.getReportDescription());

        return profileReport;
    }
}
