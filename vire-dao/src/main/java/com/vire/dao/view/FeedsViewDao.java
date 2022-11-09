package com.vire.dao.view;

import com.vire.dao.FeedFileDao;
import com.vire.dao.FeedsSendToDao;
import com.vire.dto.FeedsDto;
import com.vire.dto.view.FeedsViewDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_feeds")
@Data
public class FeedsViewDao {
    @Id
    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "description", length = 191)
    private String description;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<FeedCommentViewDao> feedCommentViewDaos;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<FeedLikesViewDao> feedLikesViewDaos;

    @Column(name = "updated_time", nullable = false)
    private Long updatedTime;

    public FeedsViewDto toDto() {
        var dto = new FeedsViewDto();
        dto.setFeedId(this.getFeedId());
        dto.setProfileId(this.getProfileId());
        dto.setDescription(this.getDescription());
        dto.setUpdatedTime(this.getUpdatedTime());
        dto.setFeedComments(this.feedCommentViewDaos.stream().map(dao->dao.toDto()).collect(Collectors.toList()));
        dto.setFeedLikes(this.feedLikesViewDaos.stream().map(dao->dao.toDto()).collect(Collectors.toList()));
        return dto;
    }

}
