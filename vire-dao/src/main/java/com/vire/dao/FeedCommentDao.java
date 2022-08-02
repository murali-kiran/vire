package com.vire.dao;

import com.vire.dto.FeedCommentDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="feed_comment")
@Data
public class FeedCommentDao {

    @Id
    @Column(name = "feed_comment_id")
    private Long feedCommentId;
    

    @Column(name = "commentor_profile_id", nullable = false)
    private Long commentorProfileId;

    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @OneToMany(mappedBy = "feedComment", cascade = CascadeType.ALL)
    private List<FeedCommentReplyDao> feedCommentReplyDaoList;

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

    public FeedCommentDto toDto() {

        var dto = new FeedCommentDto();

        dto.setFeedCommentId(this.getFeedCommentId());
        
        dto.setCommentorProfileId(this.getCommentorProfileId());
        dto.setFeedId(this.getFeedId());
        dto.setComment(this.getComment());
        if (this.getFeedCommentReplyDaoList() != null && !this.getFeedCommentReplyDaoList().isEmpty()) {
            dto.setFeedCommentReplyDtoList(this.getFeedCommentReplyDaoList()
                    .stream()
                    .map(expCommentReply -> expCommentReply.toDto())
                    .collect(Collectors.toList())
            );
        }
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedCommentDao fromDto(final FeedCommentDto dto) {

        var feedComment = new FeedCommentDao();

        feedComment.setFeedCommentId(dto.getFeedCommentId());
        
        feedComment.setCommentorProfileId(dto.getCommentorProfileId());
        feedComment.setFeedId(dto.getFeedId());
        feedComment.setComment(dto.getComment());

        return feedComment;
    }
}
