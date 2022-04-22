package com.vire.dao;

import com.vire.dto.SocialDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@javax.persistence.Table(name = "t_social")
@Entity
@Data
public class SocialDao {
    @javax.persistence.Id

    @javax.persistence.Column(name = "social_id", nullable = false)
    private Long socialId;

    @javax.persistence.Column(name = "profile_id", nullable = false)
    private Long profileId;

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

    @javax.persistence.Column(name = "file_id")
    private Long fileId;

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialSendToDao> sendTo;

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
    @Override
    public String toString() {
        return "SocialDao{" +
                "socialId=" + socialId +
                ", profileId=" + profileId +
                ", categoryId=" + categoryId +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", alternateContact='" + alternateContact + '\'' +
                ", fileId='" + fileId + '\'' +
                ", sendTo=" + sendTo+
                '}';
    }

    public SocialDto toDto() {
        var dto = new SocialDto();
        dto.setSocialId(this.getSocialId());
        dto.setProfileId(this.getProfileId());
        dto.setCategoryId(this.getCategoryId());
        dto.setType(this.getType());
        dto.setSubject(this.getSubject());
        dto.setDescription(this.getDescription());
        dto.setContact(this.getContact());
        dto.setAlternateContact(this.getAlternateContact());
        dto.setFileId(this.getFileId());
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
        dao.setProfileId(dto.getProfileId());
        dao.setCategoryId(dto.getCategoryId());
        dao.setType(dto.getType());
        dao.setSubject(dto.getSubject());
        dao.setDescription(dto.getDescription());
        dao.setContact(dto.getContact());
        dao.setAlternateContact(dto.getAlternateContact());
        dao.setFileId(dto.getFileId());
        dao.setCreatedTime(dto.getCreatedTime());
        dao.setUpdatedTime(dto.getUpdatedTime());

        if (dto.getSendTo() != null && !dto.getSendTo().isEmpty()) {
            dao.setSendTo(dto.getSendTo()
                    .stream()
                    .map(sendToDto -> SocialSendToDao.fromDto(sendToDto))
                    .collect(Collectors.toList())
            );
        }
        return dao;
    }
}
