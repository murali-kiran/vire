package com.vire.dao;

import com.vire.dto.FileDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_image")
@Data
public class FileDao {
    @Id
    @Column(name = "social_image_id", nullable = false)
    private Long socialImageId;

    @Column(name = "mime_type", nullable = false, length = 45)
    private String mimeType;

    @Column(name = "image_path", nullable = false, length = 191)
    private String imagePath;

    @Column(name = "image_size", nullable = false)
    private Long imageSize;

    public FileDto toDto() {
        var dto = new FileDto();
        dto.setSocialImageId(this.getSocialImageId());
        dto.setMimeType(this.getMimeType());
        dto.setImagePath(this.getImagePath());
        dto.setImageSize(this.getImageSize());
        return dto;
    }
    public static FileDao fromDto(final FileDto dto) {
        var dao = new FileDao();
        dao.setSocialImageId(dto.getSocialImageId());
        dao.setMimeType(dto.getMimeType());
        dao.setImagePath(dto.getImagePath());
        dao.setImageSize(dto.getImageSize());
        return dao;
    }
}
