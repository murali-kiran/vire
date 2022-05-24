package com.vire.model.response;

import com.vire.dto.ExperienceReportDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceReportResponse {

    private String experienceReportId;
    
    private Long experienceId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceReportResponse fromDto(final ExperienceReportDto dto) {

        var experienceReport = new ExperienceReportResponse();

        experienceReport.setExperienceReportId(String.valueOf(dto.getExperienceReportId()));
        experienceReport.setExperienceId(dto.getExperienceId());
        experienceReport.setReporterId(dto.getReporterId());
        experienceReport.setReportReason(dto.getReportReason());
        experienceReport.setReportDescription(dto.getReportDescription());
        experienceReport.setCreatedTime(dto.getCreatedTime());
        experienceReport.setUpdatedTime(dto.getUpdatedTime());

        return experienceReport;
    }
}
