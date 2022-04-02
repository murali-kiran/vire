package com.vire.dao;

import com.vire.dto.SocialPostCommentReplyDto;
import com.vire.dto.SocialPostLikeDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_like")
@Data
public class SocialPostLikeDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "liker_profile_id", nullable = false)
    private Long likerProfileId;

    @Column(name = "social_post_id", nullable = false)
    private Long socialPostId;

    @Column(name = "liked_time", nullable = false)
    private Long likedTime;

    public SocialPostLikeDto toDto(){
        var dto = new SocialPostLikeDto();
        dto.setId(this.getId());
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setLikedTime(this.getLikedTime());
        return dto;
    }

    public static SocialPostLikeDao fromDto(SocialPostLikeDto dto){
        var dao = new SocialPostLikeDao();
        dao.setId(dto.getId());
        dto.setLikerProfileId(dto.getLikerProfileId());
        dto.setSocialPostId(dto.getSocialPostId());
        dto.setLikedTime(dto.getLikedTime());
        return dao;
    }
}