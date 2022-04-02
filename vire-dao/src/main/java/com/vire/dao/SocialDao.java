package com.vire.dao;

import com.vire.dto.SocialDto;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GenerationType;

@javax.persistence.Table(name = "t_social")
@Entity
@Data
public class SocialDao {
    @javax.persistence.Id

    @javax.persistence.Column(name = "id", nullable = false)
    private Long id;

    @javax.persistence.Column(name = "user_id", nullable = false)
    private Long userId;

    @javax.persistence.Column(name = "category_id", nullable = false)
    private Long categoryId;

    @javax.persistence.Column(name = "type", length = 191)
    private String type;

    @javax.persistence.Column(name = "subject", length = 191)
    private String subject;

    @javax.persistence.Column(name = "description", length = 191)
    private String description;

    @javax.persistence.Column(name = "contact", length = 20)
    private String contact;

    @javax.persistence.Column(name = "alternate_contact", length = 20)
    private String alternateContact;

    @javax.persistence.Column(name = "image_path", length = 191)
    private String imagePath;

    @javax.persistence.Column(name = "created_time", nullable = false)
    private Long createdTime;

    @javax.persistence.Column(name = "updated_time", nullable = false)
    private Long updatedTime;

    public SocialDto toDto() {
        var dto = new SocialDto();
        dto.setId(this.getId());
        dto.setUserId(this.getUserId());
        dto.setCategoryId(this.getCategoryId());
        dto.setType(this.getType());
        dto.setSubject(this.getSubject());
        dto.setDescription(this.getDescription());
        dto.setContact(this.getContact());
        dto.setAlternateContact(this.getAlternateContact());
        dto.setImagePath(this.getImagePath());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }
    public static SocialDao fromDto(final SocialDto dto) {
        var dao = new SocialDao();

        dao.setId(dto.getId());
        dao.setUserId(dto.getUserId());
        dao.setCategoryId(dto.getCategoryId());
        dao.setType(dto.getType());
        dao.setSubject(dto.getSubject());
        dao.setDescription(dto.getDescription());
        dao.setContact(dto.getContact());
        dao.setAlternateContact(dto.getAlternateContact());
        dao.setImagePath(dto.getImagePath());
        dao.setCreatedTime(dto.getCreatedTime());
        dao.setUpdatedTime(dto.getUpdatedTime());

        return dao;
    }
}