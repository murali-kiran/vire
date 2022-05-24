package com.vire.dao;

import com.vire.dto.ChannelProfileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="channel_profile")
@Data
public class ChannelProfileDao {

    @Id
    @Column(name = "channel_profile_id")
    private Long channelProfileId;
    

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @ManyToOne
    @JoinColumn(name="channel_id", nullable=false)
    private ChannelDao channel;



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

    public ChannelProfileDto toDto() {

        var dto = new ChannelProfileDto();

        dto.setChannelProfileId(this.getChannelProfileId());
        
        dto.setProfileId(this.getProfileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ChannelProfileDao fromDto(final ChannelProfileDto dto) {

        var channelProfile = new ChannelProfileDao();

        channelProfile.setChannelProfileId(dto.getChannelProfileId());
        
        channelProfile.setProfileId(dto.getProfileId());

        return channelProfile;
    }
}
