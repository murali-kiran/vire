package com.vire.dao;

import com.vire.dto.SocialPostChatDto;
import com.vire.dto.SocialPostCommentDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_comment")
@Data
public class SocialPostCommentDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "commenter_profile_id", nullable = false)
    private Long commenterProfileId;

    @Column(name = "comment", length = 191)
    private String comment;

    @Column(name = "social_post_id", nullable = false)
    private Long socialPostId;

    @Column(name = "comment_time", nullable = false)
    private Long commentTime;

    public SocialPostCommentDto toDto(){
        var dto = new SocialPostCommentDto();
        dto.setId(this.getId());
        dto.setCommenterProfileId(this.getCommenterProfileId());
        dto.setComment(this.getComment());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setCommentTime(this.getCommentTime());
        return dto;
    }

    public static SocialPostCommentDao fromDto(SocialPostCommentDto dto){
        var dao = new SocialPostCommentDao();
        dao.setId(dto.getId());
        dao.setCommenterProfileId(dto.getCommenterProfileId());
        dao.setComment(dto.getComment());
        dao.setSocialPostId(dto.getSocialPostId());
        dao.setCommentTime(dto.getCommentTime());
        return dao;
    }
}