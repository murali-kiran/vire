package com.vire.dao;

import com.vire.dto.CommentReplyDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_social_post_comment_reply")
@Data
public class CommentReplyDao extends BaseDao{
    @Id
    @Column(name = "social_post_comment_reply_id", nullable = false)
    private Long socialPostCommentReplyId;

    @Column(name = "comment_replier_profile_id", nullable = false)
    private Long commentReplierProfileId;

    @Column(name = "reply", length = 191)
    private String reply;

    /*@Column(name = "comment_id", nullable = false)
    private Long commentId;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @ToString.Exclude
    private CommentDao socialPostComment;

    @Column(name = "social_id", nullable = false)
    private Long socialId;

    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public CommentReplyDto toDto(){
        var dto = new CommentReplyDto();
        dto.setSocialPostCommentReplyId(this.getSocialPostCommentReplyId());
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId());
        dto.setReply(this.getReply());
        dto.setCommentId(this.getSocialPostComment().getSocialPostCommentId());
        dto.setSocialId(this.getSocialId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }

    public static CommentReplyDao fromDto(CommentReplyDto dto){
        var dao = new CommentReplyDao();
        dao.setSocialPostCommentReplyId(dto.getSocialPostCommentReplyId());
        dao.setCommentReplierProfileId(dto.getCommentReplierProfileId());
        dao.setReply(dto.getReply());
        //dao.setCommentId(dto.getCommentId());
        dao.setSocialId(dto.getSocialId());
        return dao;
    }
}