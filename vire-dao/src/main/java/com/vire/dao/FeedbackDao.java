package com.vire.dao;

import com.vire.dto.FeedbackDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="Feedback")
@Data
public class FeedbackDao {

    @Id
    @Column(name = "feedback_id")
    private Long feedbackId;
    

    @Column(name = "feedback_provider_id", nullable = false)
    private Long feedback_provider_id;

    @Column(name = "rating", nullable = false)
    private Short rating;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public FeedbackDto toDto() {

        var dto = new FeedbackDto();

        dto.setFeedbackId(this.getFeedbackId());
        
        dto.setFeedback_provider_id(this.getFeedback_provider_id());
        dto.setRating(this.getRating());
        dto.setDescription(this.getDescription());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedbackDao fromDto(final FeedbackDto dto) {

        var feedback = new FeedbackDao();

        feedback.setFeedbackId(dto.getFeedbackId());
        
        feedback.setFeedback_provider_id(dto.getFeedback_provider_id());
        feedback.setRating(dto.getRating());
        feedback.setDescription(dto.getDescription());

        return feedback;
    }
}
