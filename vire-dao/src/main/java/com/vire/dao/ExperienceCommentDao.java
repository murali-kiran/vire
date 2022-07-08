package com.vire.dao;

import com.vire.dto.ExperienceCommentDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="experience_comment")
@Data
public class ExperienceCommentDao {

    @Id
    @Column(name = "experience_comment_id")
    private Long experienceCommentId;

    @Column(name = "commentor_profile_id", nullable = false)
    private Long commentorProfileId;

    @Column(name = "experience_id", nullable = false)
    private Long experienceId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @OneToMany(mappedBy = "experienceComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceCommentReplyDao> expCommentReplyDaoList;

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

    public ExperienceCommentDto toDto() {

        var dto = new ExperienceCommentDto();

        dto.setExperienceCommentId(this.getExperienceCommentId());
        
        dto.setCommentorProfileId(this.getCommentorProfileId());
        dto.setExperienceId(this.getExperienceId());
        dto.setComment(this.getComment());
        if (this.getExpCommentReplyDaoList() != null && !this.getExpCommentReplyDaoList().isEmpty()) {
            dto.setExperienceCommentReplyDtoList(this.getExpCommentReplyDaoList()
                    .stream()
                    .map(expCommentReply -> expCommentReply.toDto())
                    .collect(Collectors.toList())
            );
        }
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ExperienceCommentDao fromDto(final ExperienceCommentDto dto) {

        var experienceComment = new ExperienceCommentDao();

        experienceComment.setExperienceCommentId(dto.getExperienceCommentId());
        
        experienceComment.setCommentorProfileId(dto.getCommentorProfileId());
        experienceComment.setExperienceId(dto.getExperienceId());
        experienceComment.setComment(dto.getComment());

        return experienceComment;
    }
}
