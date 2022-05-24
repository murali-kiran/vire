package com.vire.dao;

import com.vire.dto.ChannelDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="channel")
@Data
public class ChannelDao {

    @Id
    @Column(name = "channel_id")
    private Long channelId;
    

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creator_profile_id", nullable = false)
    private Long creatorProfileId;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "rules", nullable = false)
    private String rules;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelProfileDao> profiles;

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

    public ChannelDto toDto() {

        var dto = new ChannelDto();

        dto.setChannelId(this.getChannelId());
        
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setCreatorProfileId(this.getCreatorProfileId());
        dto.setFileId(this.getFileId());
        dto.setRules(this.getRules());

        if (this.getProfiles() != null && !this.getProfiles().isEmpty()) {
            dto.setProfiles(this.getProfiles()
                    .stream()
                    .map(child -> child.toDto())
                    .collect(Collectors.toList()));
        }


        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static ChannelDao fromDto(final ChannelDto dto) {

        var channel = new ChannelDao();

        channel.setChannelId(dto.getChannelId());
        
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        channel.setCreatorProfileId(dto.getCreatorProfileId());
        channel.setFileId(dto.getFileId());
        channel.setRules(dto.getRules());

        if (dto.getProfiles() != null && !dto.getProfiles().isEmpty()) {
            channel.setProfiles(dto.getProfiles()
                    .stream()
                    .map(child -> ChannelProfileDao.fromDto(child))
                    .collect(Collectors.toList()));
        }


        return channel;
    }
}
