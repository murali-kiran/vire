package com.vire.model.response;

import com.vire.dto.ProfileReportDto;
import lombok.Data;

@Data
public class ProfileReportResponse {

    private String profileReportId;
    
    private String profileId;
    private String reporterId;
    private String reportReason;
    private String reportDescription;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileReportResponse fromDto(final ProfileReportDto dto) {

        var profileReport = new ProfileReportResponse();

        profileReport.setProfileReportId(String.valueOf(dto.getProfileReportId()));
        profileReport.setProfileId(String.valueOf(dto.getProfileId()));
        profileReport.setReporterId(String.valueOf(dto.getReporterId()));
        profileReport.setReportReason(dto.getReportReason());
        profileReport.setReportDescription(dto.getReportDescription());
        profileReport.setCreatedTime(dto.getCreatedTime());
        profileReport.setUpdatedTime(dto.getUpdatedTime());

        return profileReport;
    }
}
