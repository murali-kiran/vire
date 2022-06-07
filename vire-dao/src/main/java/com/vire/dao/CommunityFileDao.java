package com.vire.dao;

import com.vire.dto.CommunityFileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="community_files")
@Data
public class CommunityFileDao {

    @Id
    @Column(name = "community_files_id")
    private Long communityFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne
    @JoinColumn(name="community_id", nullable=false)
    private CommunityDao community;



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

    public CommunityFileDto toDto() {

        var dto = new CommunityFileDto();

        dto.setCommunityFileId(this.getCommunityFileId());
        
        dto.setFileId(this.getFileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static CommunityFileDao fromDto(final CommunityFileDto dto) {

        var communityFile = new CommunityFileDao();

        communityFile.setCommunityFileId(dto.getCommunityFileId());
        
        communityFile.setFileId(dto.getFileId());

        return communityFile;
    }
}
