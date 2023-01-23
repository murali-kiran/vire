package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedReportDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedReportRequest {

    private String feedReportId;
    
    private String feedId;
    private String reporterId;
    private String reportReason;
    private String reportDescription;

    public FeedReportDto toDto(Snowflake snowflake) {

        var dto = new FeedReportDto();

        if(snowflake == null) {
            dto.setFeedReportId(this.getFeedReportId() == null ? null : Long.valueOf(this.getFeedReportId()));
        }else {
            dto.setFeedReportId(snowflake.nextId());
        }
        
        dto.setFeedId(this.getFeedId() == null ? null : Long.valueOf(this.getFeedId()));
        dto.setReporterId(this.getReporterId() == null ? null : Long.valueOf(this.getReporterId()));
        dto.setReportReason(this.getReportReason());
        dto.setReportDescription(this.getReportDescription());

        return dto;
    }

    public FeedReportDto toDto() {
        return toDto(null);
    }
}