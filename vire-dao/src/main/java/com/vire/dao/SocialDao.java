package com.vire.dao;

import com.vire.dto.SocialDto;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@javax.persistence.Table(name = "t_social")
@Entity
@Data
public class SocialDao extends BaseDao{
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

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialPostSendToDao> sendTo;

    public SocialDto toDto() {
        var dto = new SocialDto();
        dto.setSocialId(this.getSocialId());
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

        if (this.getSendTo() != null && !this.getSendTo().isEmpty()) {
            dto.setSendTo(this.getSendTo()
                    .stream()
                    .map(sendTo -> sendTo.toDto())
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static SocialDao fromDto(final SocialDto dto) {
        var dao = new SocialDao();

        dao.setSocialId(dto.getSocialId());
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

        if (dto.getSendTo() != null && !dto.getSendTo().isEmpty()) {
            dao.setSendTo(dto.getSendTo()
                    .stream()
                    .map(sendToDto -> SocialPostSendToDao.fromDto(sendToDto))
                    .collect(Collectors.toList())
            );
        }
        return dao;
    }
}
