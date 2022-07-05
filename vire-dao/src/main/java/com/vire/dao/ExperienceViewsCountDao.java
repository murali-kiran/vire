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
    private Long experienceId;

    @Column(name = "views_count")
    private Integer viewsCount;
    public ExperienceViewsCountDto toDto() {

        var dto = new ExperienceViewsCountDto();
        dto.setExperienceId(this.getExperienceId());
        dto.setViewsCount(this.getViewsCount());
        return dto;
    }
    public static ExperienceViewsCountDao fromDto(final ExperienceViewsCountDto dto) {

        var experience = new ExperienceViewsCountDao();
        experience.setExperienceId(dto.getExperienceId());
        experience.setViewsCount(dto.getViewsCount());
        return experience;
    }
}