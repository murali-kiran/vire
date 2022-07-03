package com.vire.dao;

import com.vire.dto.ExperienceCommentReplyDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="experience_comment_reply")
@Data
public class ExperienceCommentReplyDao {

    @Id
    @Column(name = "experience_comment_reply_id")
    private Long experienceCommentReplyId;

    @Column(name = "replier_profile_id", nullable = false)
    private Long replierProfileId;

    @Column(name = "experience_id", nullable = false)
    private Long experienceId;

   /* @Column(name = "comment_id", nullable = false)
    private Long commentId;*/
   @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   @JoinColumn(name = "comment_id")
   @ToString.Exclude
   private ExperienceCommentDao experienceComment;

    @Column(name = "reply", nullable = false)
    private String reply;

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

    public ExperienceCommentReplyDto toDto() {

        var dto = new ExperienceCommentReplyDto();

        dto.setExperienceCommentReplyId(this.getExperienceCommentReplyId());
        
        dto.setReplierProfileId(this.getReplierProfileId());
        dto.setExperienceId(this.getExperienceId());
        dto.setCommentId(this.getExperienceComment().getExperienceCommentId());
        dto.setReply(this.getReply());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ExperienceCommentReplyDao fromDto(final ExperienceCommentReplyDto dto) {

        var experienceCommentReply = new ExperienceCommentReplyDao();

        experienceCommentReply.setExperienceCommentReplyId(dto.getExperienceCommentReplyId());
        
        experienceCommentReply.setReplierProfileId(dto.getReplierProfileId());
        experienceCommentReply.setExperienceId(dto.getExperienceId());
        //experienceCommentReply.setCommentId(dto.getCommentId());
        experienceCommentReply.setReply(dto.getReply());

        return experienceCommentReply;
    }
}
