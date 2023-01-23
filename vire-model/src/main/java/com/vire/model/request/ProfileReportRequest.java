package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ProfileReportDto;
import lombok.Data;


@Data
public class ProfileReportRequest {

    private String profileReportId;
    
    private String profileId;
    private String reporterId;
    private String reportReason;
    private String reportDescription;

    public ProfileReportDto toDto(Snowflake snowflake) {

        var dto = new ProfileReportDto();

        if(snowflake == null) {
            dto.setProfileReportId(this.getProfileReportId() == null ? null : Long.valueOf(this.getProfileReportId()));
        }else {
            dto.setProfileReportId(this.getProfileReportId() == null ? snowflake.nextId() : Long.valueOf(this.getProfileReportId()));
        }
        
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setReporterId(this.getReporterId() == null ? null : Long.valueOf(this.getReporterId()));
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        return dto;
    }

    public ProfileReportDto toDto() {
        return toDto(null);
    }
}