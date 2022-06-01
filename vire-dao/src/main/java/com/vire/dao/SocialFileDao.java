package com.vire.dao;

import com.vire.dto.SocialFileDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="social_file")
@Data
public class SocialFileDao {

    @Id
    @Column(name = "social_file_id")
    private Long socialFileId;
    

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "social_id")
    @ToString.Exclude
    private SocialDao social;

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

    public SocialFileDto toDto() {

        var dto = new SocialFileDto();

        dto.setSocialFileId(this.getSocialFileId());
        
        dto.setFileId(this.getFileId());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialFileDao fromDto(final SocialFileDto dto) {

        var socialFile = new SocialFileDao();

        socialFile.setSocialFileId(dto.getSocialFileId());
        
        socialFile.setFileId(dto.getFileId());

        return socialFile;
    }
}
