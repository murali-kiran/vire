package com.vire.dao;

import com.vire.dto.LikesDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_like")
@Data
public class LikesDao {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "liker_profile_id", nullable = false)
    private Long likerProfileId;

    @Column(name = "social_post_id", nullable = false)
    private Long socialPostId;

    @Column(name = "liked_time", nullable = false)
    private Long likedTime;

    public LikesDto toDto(){
        var dto = new LikesDto();
        dto.setId(this.getId());
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setLikedTime(this.getLikedTime());
        return dto;
    }

    public static LikesDao fromDto(LikesDto dto){
        var dao = new LikesDao();
        dao.setId(dto.getId());
        dao.setLikerProfileId(dto.getLikerProfileId());
        dao.setSocialPostId(dto.getSocialPostId());
        dao.setLikedTime(dto.getLikedTime());
        return dao;
    }
}