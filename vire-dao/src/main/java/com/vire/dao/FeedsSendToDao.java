package com.vire.dao;

import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialSendToDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_feeds_send_to")
@Data
public class FeedsSendToDao {
    @Id
    @Column(name = "t_feeds_send_to_id", nullable = false)
    private Long feedsSendToId;

    @Column(name = "type", length = 191)
    private String type;

    @Column(name = "value", length = 191)
    private String value;

    @Column(name = "name", length = 191)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feed_id", nullable = false)
    private FeedsDao feed;

    @Column(name = "created_time", nullable = false)
    private Long createdTime;

    @Column(name = "updated_time", nullable = false)
    private Long updatedTime;
    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @Override
    public String toString() {
        return "FeedsSendToDao{" +
                "feedsSendToId=" + feedsSendToId +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", feed=" + feed.getFeedId() +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

    public FeedsSendToDto toDto(){
        var dto = new FeedsSendToDto();
        dto.setFeedsSendToId  (this.getFeedsSendToId());
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        dto.setName(this.getName());
        dto.setFeedId(this.getFeed().getFeedId());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }

    public static FeedsSendToDao fromDto(FeedsSendToDto dto){
        var dao = new FeedsSendToDao();
        dao.setFeedsSendToId(dto.getFeedsSendToId());
        dao.setType(dto.getType());
        dao.setValue(dto.getValue());
        dao.setName(dto.getName());
        return dao;
    }
}