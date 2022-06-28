package com.vire.model.response;

import com.vire.dto.SocialReportDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialReportResponse {

    private String socialReportId;
    
    private String socialId;
    private String reporterId;
    private String reportReason;
    private String reportDescription;
    private Long createdTime;
    private Long updatedTime;

    public static SocialReportResponse fromDto(final SocialReportDto dto) {

        var socialReport = new SocialReportResponse();

        socialReport.setSocialReportId(String.valueOf(dto.getSocialReportId()));
        socialReport.setSocialId(String.valueOf(dto.getSocialId()));
        socialReport.setReporterId(String.valueOf(dto.getReporterId()));
        socialReport.setReportReason(dto.getReportReason());
        socialReport.setReportDescription(dto.getReportDescription());
        socialReport.setCreatedTime(dto.getCreatedTime());
        socialReport.setUpdatedTime(dto.getUpdatedTime());

        return socialReport;
    }
}
