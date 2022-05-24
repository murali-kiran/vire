package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class FeedbackDto {
    private Long feedbackId;
    private Long feedback_provider_id;
    private Short rating;
    private String description;
    public Long createdTime;
    public Long updatedTime;
}
