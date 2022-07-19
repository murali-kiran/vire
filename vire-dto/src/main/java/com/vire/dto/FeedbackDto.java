package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class FeedbackDto {
    private Long feedbackId;
    private Long feedbackProviderId;
    private Short rating;
    private String description;
    public Long createdTime;
    public Long updatedTime;
}
