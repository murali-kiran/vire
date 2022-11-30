package com.vire.dao.view;

import com.vire.dao.ExperienceFileDao;
import com.vire.dto.ExperienceDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="experience")
@Data
public class ExperienceViewDao {

    @Id
    @Column(name = "experience_id")
    private Long experienceId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @Column(name = "deleted_time")
    public Long deletedTime;

    public ExperienceDto toDto() {

        var dto = new ExperienceDto();

        dto.setExperienceId(this.getExperienceId());

        dto.setCategoryId(this.getCategoryId());
        dto.setProfileId(this.getProfileId());
        dto.setTitle(this.getTitle());
        dto.setDescription(this.getDescription());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

}
