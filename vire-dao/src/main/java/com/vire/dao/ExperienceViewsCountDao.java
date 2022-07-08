package com.vire.dao;

import com.vire.dto.ExperienceViewsCountDto;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "experience_views_count")
public class ExperienceViewsCountDao {
    @Id
    @Column(name = "views_count_id", nullable = false)
    private Long viewsCountId;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "experience_id", nullable = false)
    private Long experienceId;

    public ExperienceViewsCountDto toDto() {

        var dto = new ExperienceViewsCountDto();
        dto.setViewsCountId(this.getViewsCountId());
        dto.setExperienceId(this.getExperienceId());
        dto.setProfileId(this.getProfileId());
        return dto;
    }
    public static ExperienceViewsCountDao fromDto(final ExperienceViewsCountDto dto) {

        var experience = new ExperienceViewsCountDao();
        experience.setExperienceId(dto.getExperienceId());
        experience.setViewsCountId(dto.getViewsCountId());
        experience.setProfileId(dto.getProfileId());
        return experience;
    }
}