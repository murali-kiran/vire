package com.vire.dao;

import com.vire.dto.SocialImageDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_social_image")
@Data
public class SocialImageDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_image_id", nullable = false)
    private Long socialImageId;

    @Column(name = "mime_type", nullable = false, length = 45)
    private String mimeType;

    @Column(name = "image_path", nullable = false, length = 191)
    private String imagePath;

    @Column(name = "image_size", nullable = false)
    private Long imageSize;

    public SocialImageDto toDto() {
        var dto = new SocialImageDto();
        dto.setSocialImageId(this.getSocialImageId());
        dto.setMimeType(this.getMimeType());
        dto.setImagePath(this.getImagePath());
        dto.setImageSize(this.getImageSize());
        return dto;
    }
    public static SocialImageDao fromDto(final SocialImageDto dto) {
        var dao = new SocialImageDao();
        dao.setSocialImageId(dto.getSocialImageId());
        dao.setMimeType(dto.getMimeType());
        dao.setImagePath(dto.getImagePath());
        dao.setImageSize(dto.getImageSize());
        return dao;
    }
}
