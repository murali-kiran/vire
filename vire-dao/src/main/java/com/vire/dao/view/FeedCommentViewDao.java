package com.vire.dao.view;

import com.vire.dao.FeedCommentReplyDao;
import com.vire.dao.FeedsDao;
import com.vire.dto.FeedCommentDto;
import com.vire.dto.view.FeedCommentViewDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="feed_comment")
@Data
public class FeedCommentViewDao {

    @Id
    @Column(name = "feed_comment_id")
    private Long feedCommentId;
    

    @Column(name = "commentor_profile_id", nullable = false)
    private Long commentorProfileId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feed_id", nullable = false)
    private FeedsViewDao feed;

    @Column(name = "comment", nullable = false)
    private String comment;


    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    public FeedCommentViewDto toDto() {

        var dto = new FeedCommentViewDto();

        dto.setFeedCommentId(this.getFeedCommentId());
        
        dto.setCommentorProfileId(this.getCommentorProfileId());
        dto.setFeedId(this.getFeed().getFeedId());
        dto.setComment(this.getComment());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

}
