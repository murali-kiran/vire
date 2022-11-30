package com.vire.dao.view;

import com.vire.dao.SocialFileDao;
import com.vire.dao.SocialSendToDao;
import com.vire.dto.SocialDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "t_social")
@Entity
@Data
public class SocialViewDao {
    @Id
    @Column(name = "social_id", nullable = false)
    private Long socialId;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "description", length = 191)
    private String description;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @Column(name = "deleted_time")
    public Long deletedTime;


    public SocialDto toDto() {
        var dto = new SocialDto();
        dto.setSocialId(this.getSocialId());
        dto.setProfileId(this.getProfileId());
        dto.setCategoryId(this.getCategoryId());
        dto.setDescription(this.getDescription());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

}
