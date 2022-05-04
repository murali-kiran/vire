package com.vire.dao;

import com.vire.dto.FeedCommentReplyDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="feed_comment_reply")
@Data
public class FeedCommentReplyDao {

    @Id
    @Column(name = "feed_comment_reply_id")
    private Long feedCommentReplyId;
    

    @Column(name = "replier_profile_id", nullable = false)
    private Long replierProfileId;

    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

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

    public FeedCommentReplyDto toDto() {

        var dto = new FeedCommentReplyDto();

        dto.setFeedCommentReplyId(this.getFeedCommentReplyId());
        
        dto.setReplierProfileId(this.getReplierProfileId());
        dto.setFeedId(this.getFeedId());
        dto.setCommentId(this.getCommentId());
        dto.setReply(this.getReply());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedCommentReplyDao fromDto(final FeedCommentReplyDto dto) {

        var feedCommentReply = new FeedCommentReplyDao();

        feedCommentReply.setFeedCommentReplyId(dto.getFeedCommentReplyId());
        
        feedCommentReply.setReplierProfileId(dto.getReplierProfileId());
        feedCommentReply.setFeedId(dto.getFeedId());
        feedCommentReply.setCommentId(dto.getCommentId());
        feedCommentReply.setReply(dto.getReply());

        return feedCommentReply;
    }
}
