package com.vire.dao;

import com.vire.dto.SocialCategoryTypeMasterDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="social_category_type_master")
@Data
public class SocialCategoryTypeMasterDao {

    @Id
    @Column(name = "social_category_type_master_id")
    private Long socialCategoryTypeMasterId;
    

    @Column(name = "category_type", nullable = false)
    private String categoryType;

    @ManyToOne
    @JoinColumn(name="social_category_master_id", nullable=false)
    private SocialCategoryMasterDao socialCategoryMaster;



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

    public SocialCategoryTypeMasterDto toDto() {

        var dto = new SocialCategoryTypeMasterDto();

        dto.setSocialCategoryTypeMasterId(this.getSocialCategoryTypeMasterId());
        
        dto.setCategoryType(this.getCategoryType());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialCategoryTypeMasterDao fromDto(final SocialCategoryTypeMasterDto dto) {

        var socialCategoryTypeMaster = new SocialCategoryTypeMasterDao();

        socialCategoryTypeMaster.setSocialCategoryTypeMasterId(dto.getSocialCategoryTypeMasterId());
        
        socialCategoryTypeMaster.setCategoryType(dto.getCategoryType());

        return socialCategoryTypeMaster;
    }
}
