package com.vire.dao;

import com.vire.dto.FeedLikesDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="feed_likes")
@Data
public class FeedLikesDao {

    @Id
    @Column(name = "feed_likes_id")
    private Long feedLikesId;
    

    @Column(name = "liker_profile_id", nullable = false)
    private Long likerProfileId;

    @Column(name = "feed_id", nullable = false)
    private Long feedId;

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

    public FeedLikesDto toDto() {

        var dto = new FeedLikesDto();

        dto.setFeedLikesId(this.getFeedLikesId());
        
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setFeedId(this.getFeedId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static FeedLikesDao fromDto(final FeedLikesDto dto) {

        var feedLikes = new FeedLikesDao();

        feedLikes.setFeedLikesId(dto.getFeedLikesId());
        
        feedLikes.setLikerProfileId(dto.getLikerProfileId());
        feedLikes.setFeedId(dto.getFeedId());

        return feedLikes;
    }
}
