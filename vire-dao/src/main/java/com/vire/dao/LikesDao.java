package com.vire.dao;

import com.vire.dto.LikesDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_social_post_like")
@Data
public class LikesDao extends BaseDao{
    @Id
    @Column(name = "social_post_like_id", nullable = false)
    private Long socialPostLikeId;

    @Column(name = "liker_profile_id", nullable = false)
    private Long likerProfileId;

    @Column(name = "liked_time", nullable = false)
    private Long likedTime;

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

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public LikesDto toDto(){
        var dto = new LikesDto();
        dto.setId(this.getSocialPostLikeId());
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setLikedTime(this.getLikedTime());
        dto.setSocialId(this.getSocialId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }

    public static LikesDao fromDto(LikesDto dto){
        var dao = new LikesDao();
        dao.setSocialPostLikeId(dto.getId());
        dao.setLikerProfileId(dto.getLikerProfileId());
        dao.setSocialId(dto.getSocialId());
        dao.setLikedTime(dto.getLikedTime());
        return dao;
    }
}