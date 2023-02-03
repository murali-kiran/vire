package com.vire.dao;

import com.vire.dto.AppRestrictionDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="t_app_restriction")
@Data
public class AppRestrictionDao {

    @Id
    @Column(name = "t_app_restriction_id")
    private Long appRestrictionId;
    

    @Column(name = "interests_selection_limit", nullable = false)
    private Integer InterestsSelectionLimit;

    @Column(name = "creating_communities_limit", nullable = false)
    private Integer CreatingCommunitiesLimit;

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

    public AppRestrictionDto toDto() {

        var dto = new AppRestrictionDto();

        dto.setAppRestrictionId(this.getAppRestrictionId());
        
        dto.setInterestsSelectionLimit(this.getInterestsSelectionLimit());
        dto.setCreatingCommunitiesLimit(this.getCreatingCommunitiesLimit());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static AppRestrictionDao fromDto(final AppRestrictionDto dto) {

        var appRestriction = new AppRestrictionDao();

        appRestriction.setAppRestrictionId(dto.getAppRestrictionId());
        
        appRestriction.setInterestsSelectionLimit(dto.getInterestsSelectionLimit());
        appRestriction.setCreatingCommunitiesLimit(dto.getCreatingCommunitiesLimit());

        return appRestriction;
    }
}
