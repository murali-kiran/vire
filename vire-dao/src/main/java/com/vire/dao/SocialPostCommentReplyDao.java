package com.vire.dao;

import com.vire.dto.SocialPostCommentDto;
import com.vire.dto.SocialPostCommentReplyDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_comment_reply")
@Data
public class SocialPostCommentReplyDao {
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

    public SocialPostCommentReplyDto toDto(){
        var dto = new SocialPostCommentReplyDto();
        dto.setId(this.getId());
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId());
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId());
        dto.setReplyTime(this.getReplyTime());
        return dto;
    }

    public static SocialPostCommentReplyDao fromDto(SocialPostCommentReplyDto dto){
        var dao = new SocialPostCommentReplyDao();
        dao.setId(dto.getId());
        dao.setCommentReplierProfileId(dto.getCommentReplierProfileId());
        dao.setReply(dto.getReply());
        dao.setCommentId(dto.getCommentId());
        dao.setReplyTime(dto.getReplyTime());
        return dao;
    }
}