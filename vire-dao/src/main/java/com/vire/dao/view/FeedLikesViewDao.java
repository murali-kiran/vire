package com.vire.dao.view;

import com.vire.dto.FeedLikesDto;
import com.vire.dto.view.FeedLikesViewDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="feed_likes")
@Data
public class FeedLikesViewDao {

    @Id
    @Column(name = "feed_likes_id")
    private Long feedLikesId;
    

    @Column(name = "liker_profile_id", nullable = false)
    private Long likerProfileId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feed_id", nullable = false)
    private FeedsViewDao feed;

    @Column(name = "created_time", nullable = false , updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    public FeedLikesViewDto toDto() {

        var dto = new FeedLikesViewDto();
        dto.setFeedLikesId(this.getFeedLikesId());
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

}
