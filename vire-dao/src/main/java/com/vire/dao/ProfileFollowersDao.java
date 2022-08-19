package com.vire.dao;

import com.vire.dto.ProfileFollowersDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="profile_followers")
@Data
public class ProfileFollowersDao {

    @Id
    @Column(name = "profile_followers_id")
    private Long profileFollowersId;


    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @Column(name = "is_friend")
    private Boolean isFriend;

    @Column(name = "status", nullable = false)
    private String status;

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

    public ProfileFollowersDto toDto() {

        var dto = new ProfileFollowersDto();

        dto.setProfileFollowersId(this.getProfileFollowersId());
        
        dto.setProfileId(this.getProfileId());
        dto.setFollowerId(this.getFollowerId());
        dto.setIsFriend(this.getIsFriend());
        dto.setStatus(this.getStatus());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ProfileFollowersDao fromDto(final ProfileFollowersDto dto) {

        var profileFollowers = new ProfileFollowersDao();

        profileFollowers.setProfileFollowersId(dto.getProfileFollowersId());
        
        profileFollowers.setProfileId(dto.getProfileId());
        profileFollowers.setFollowerId(dto.getFollowerId());
        profileFollowers.setIsFriend(dto.getIsFriend());
        profileFollowers.setStatus(dto.getStatus());
        return profileFollowers;
    }
}
