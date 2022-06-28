package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.SocialReportDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialReportRequest {

    private String socialReportId;
    
    private String socialId;
    private String reporterId;
    private String reportReason;
    private String reportDescription;

    public SocialReportDto toDto(Snowflake snowflake) {

        var dto = new SocialReportDto();

        if(snowflake == null) {
            dto.setSocialReportId(this.getSocialReportId() == null ? null : Long.valueOf(this.getSocialReportId()));
        }else {
            dto.setSocialReportId(snowflake.nextId());
        }
        
        dto.setSocialId(this.getSocialId() == null ? null : Long.valueOf(this.getSocialId()));
        dto.setReporterId(this.getReporterId() == null ? null : Long.valueOf(this.getReporterId()));
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        return dto;
    }

    public SocialReportDto toDto() {
        return toDto(null);
    }
}