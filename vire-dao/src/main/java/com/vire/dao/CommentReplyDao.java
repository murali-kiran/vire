package com.vire.dao;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_comment_reply")
@Data
public class CommentReplyDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "comment_replier_profile_id", nullable = false)
    private Long commentReplierProfileId;

    @Column(name = "reply", length = 191)
    private String reply;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "reply_time", nullable = false)
    private Long replyTime;

    public CommentReplyDto toDto(){
        var dto = new CommentReplyDto();
        dto.setId(this.getId());
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId());
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId());
        dto.setReplyTime(this.getReplyTime());
        return dto;
    }

    public static CommentReplyDao fromDto(CommentReplyDto dto){
        var dao = new CommentReplyDao();
        dao.setId(dto.getId());
        dao.setCommentReplierProfileId(dto.getCommentReplierProfileId());
        dao.setReply(dto.getReply());
        dao.setCommentId(dto.getCommentId());
        dao.setReplyTime(dto.getReplyTime());
        return dao;
    }
}