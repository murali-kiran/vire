package com.vire.model.response;

import com.vire.dto.FeedbackDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedbackResponse {

    private String feedbackId;
    
    private String feedbackProviderId;
    private Short rating;
    private String description;
    private Long createdTime;
    private Long updatedTime;

    public static FeedbackResponse fromDto(final FeedbackDto dto) {

        var feedback = new FeedbackResponse();

        feedback.setFeedbackId(String.valueOf(dto.getFeedbackId()));
        feedback.setFeedbackProviderId(dto.getFeedbackProviderId() == null ? null : String.valueOf(dto.getFeedbackProviderId()));
        feedback.setRating(dto.getRating());
        feedback.setDescription(dto.getDescription());
        feedback.setCreatedTime(dto.getCreatedTime());
        feedback.setUpdatedTime(dto.getUpdatedTime());

        return feedback;
    }
}
