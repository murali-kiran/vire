package com.vire.dao;

import com.vire.dto.CommunityProfileFileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="community_profile_files")
@Data
public class CommunityProfileFileDao {

    @Id
    @Column(name = "community_profile_files_id")
    private Long communityProfileFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne
    @JoinColumn(name="community_profile_id", nullable=false)
    private CommunityProfileDao communityProfile;



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

    public CommunityProfileFileDto toDto() {

        var dto = new CommunityProfileFileDto();

        dto.setCommunityProfileFileId(this.getCommunityProfileFileId());
        
        dto.setFileId(this.getFileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static CommunityProfileFileDao fromDto(final CommunityProfileFileDto dto) {

        var communityProfileFile = new CommunityProfileFileDao();

        communityProfileFile.setCommunityProfileFileId(dto.getCommunityProfileFileId());
        
        communityProfileFile.setFileId(dto.getFileId());

        return communityProfileFile;
    }
}
