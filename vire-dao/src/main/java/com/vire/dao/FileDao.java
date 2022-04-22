package com.vire.dao;

import com.vire.dto.FileDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_file")
@Data
public class FileDao extends BaseDao{
    @Id
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "mime_type", nullable = false, length = 45)
    private String mimeType;

    @Column(name = "file_common_path", nullable = false, length = 191)
    private String fileCommonPath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;
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

    public FileDto toDto() {
        var dto = new FileDto();
        dto.setFileId(this.getFileId());
        dto.setMimeType(this.getMimeType());
        dto.setFileCommonPath(this.getFileCommonPath());
        dto.setFileSize(this.getFileSize());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }
    public static FileDao fromDto(final FileDto dto) {
        var dao = new FileDao();
        dao.setFileId(dto.getFileId());
        dao.setMimeType(dto.getMimeType());
        dao.setFileCommonPath(dto.getFileCommonPath());
        dao.setFileSize(dto.getFileSize());
        return dao;
    }
}
