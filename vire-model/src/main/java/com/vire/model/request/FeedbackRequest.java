package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedbackDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedbackRequest {

    private String feedbackId;
    
    private Long feedback_provider_id;
    private Short rating;
    private String description;

    public FeedbackDto toDto(Snowflake snowflake) {

        var dto = new FeedbackDto();

        if(snowflake == null) {
            dto.setFeedbackId(this.getFeedbackId() == null ? null : Long.valueOf(this.getFeedbackId()));
        }else {
            dto.setFeedbackId(snowflake.nextId());
        }
        
        dto.setFeedback_provider_id(this.getFeedback_provider_id());
        dto.setRating(this.getRating());
        dto.setDescription(this.getDescription());

        return dto;
    }

    public FeedbackDto toDto() {
        return toDto(null);
    }
}