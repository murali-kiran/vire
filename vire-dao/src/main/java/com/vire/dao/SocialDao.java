package com.vire.dao;

import com.vire.dto.PersonalProfileDto;
import com.vire.dto.SocialDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;

@javax.persistence.Table(name = "t_social")
@Entity
@Data
public class SocialDao {
    @javax.persistence.Id

    @javax.persistence.Column(name = "id", nullable = false)
    private Long socialId;


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

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialPostSendToDao> sendTo;

    public SocialDto toDto() {
        return new ModelMapper().map(this,SocialDto.class);
    }

    public static SocialDao fromDto(final SocialDto dto) {
        return new ModelMapper().map(dto, SocialDao.class);
    }
}