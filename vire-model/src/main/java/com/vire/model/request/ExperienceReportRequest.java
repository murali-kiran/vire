package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ExperienceReportDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceReportRequest {

    private String experienceReportId;
    
    private Long experienceId;
    private Long reporterId;
    private String reportReason;
    private String reportDescription;

    public ExperienceReportDto toDto(Snowflake snowflake) {

        var dto = new ExperienceReportDto();

        if(snowflake == null) {
            dto.setExperienceReportId(this.getExperienceReportId() == null ? null : Long.valueOf(this.getExperienceReportId()));
        }else {
            dto.setExperienceReportId(snowflake.nextId());
        }
        
        dto.setExperienceId(this.getExperienceId());
        dto.setReporterId(this.getReporterId());
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        return dto;
    }

    public ExperienceReportDto toDto() {
        return toDto(null);
    }
}