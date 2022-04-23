package com.vire.dao;

import com.vire.dto.CommentDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_social_post_comment")
@Data
public class CommentDao extends BaseDao{
    @Id
    @Column(name = "social_post_comment_id", nullable = false)
    private Long socialPostCommentId;

    @Column(name = "commenter_profile_id", nullable = false)
    private Long commenterProfileId;

    @Column(name = "comment", length = 191)
    private String comment;

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

    public CommentDto toDto(){
        var dto = new CommentDto();
        dto.setSocialPostCommentId(this.getSocialPostCommentId());
        dto.setCommenterProfileId(this.getCommenterProfileId());
        dto.setComment(this.getComment());
        dto.setSocialId(this.getSocialId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }

    public static CommentDao fromDto(CommentDto dto){
        var dao = new CommentDao();
        dao.setSocialPostCommentId(dto.getSocialPostCommentId());
        dao.setCommenterProfileId(dto.getCommenterProfileId());
        dao.setComment(dto.getComment());
        dao.setSocialId(dto.getSocialId());
        return dao;
    }
}