package com.vire.dao;

import com.vire.dto.SocialCategoryMasterDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="social_category_master")
@Data
public class SocialCategoryMasterDao {

    @Id
    @Column(name = "social_category_master_id")
    private Long socialCategoryMasterId;
    

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "color_code", nullable = false)
    private String colorCode;

    @Column(name = "is_personal", nullable = false)
    private Boolean isPersonal;

    @OneToMany(mappedBy = "socialCategoryMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialCategoryTypeMasterDao> categoryTypeList;

    @OneToMany(mappedBy = "socialCategoryMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialCategorySentToMasterDao> categorySendToList;

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

    public SocialCategoryMasterDto toDto() {

        var dto = new SocialCategoryMasterDto();

        dto.setSocialCategoryMasterId(this.getSocialCategoryMasterId());
        
        dto.setCategory(this.getCategory());
        dto.setColorCode(this.getColorCode());
        dto.setIsPersonal(this.getIsPersonal());
        if (this.getCategoryTypeList() != null && !this.getCategoryTypeList().isEmpty()) {
            dto.setCategoryTypeList(this.getCategoryTypeList()
                    .stream()
                    .map(child -> child.toDto())
                    .collect(Collectors.toList()));
        }


        if (this.getCategorySendToList() != null && !this.getCategorySendToList().isEmpty()) {
            dto.setCategorySendToList(this.getCategorySendToList()
                    .stream()
                    .map(child -> child.toDto())
                    .collect(Collectors.toList()));
        }


        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialCategoryMasterDao fromDto(final SocialCategoryMasterDto dto) {

        var socialCategoryMaster = new SocialCategoryMasterDao();

        socialCategoryMaster.setSocialCategoryMasterId(dto.getSocialCategoryMasterId());
        
        socialCategoryMaster.setCategory(dto.getCategory());
        socialCategoryMaster.setColorCode(dto.getColorCode());
        socialCategoryMaster.setIsPersonal(dto.getIsPersonal());
        if (dto.getCategoryTypeList() != null && !dto.getCategoryTypeList().isEmpty()) {
            socialCategoryMaster.setCategoryTypeList(dto.getCategoryTypeList()
                    .stream()
                    .map(child -> {
                            var childDao = SocialCategoryTypeMasterDao.fromDto(child);
                            childDao.setSocialCategoryMaster(socialCategoryMaster);
                            return childDao;
                            })                    .collect(Collectors.toList()));
        }


        if (dto.getCategorySendToList() != null && !dto.getCategorySendToList().isEmpty()) {
            socialCategoryMaster.setCategorySendToList(dto.getCategorySendToList()
                    .stream()
                    .map(child -> {
                            var childDao = SocialCategorySentToMasterDao.fromDto(child);
                            childDao.setSocialCategoryMaster(socialCategoryMaster);
                            return childDao;
                            })                    .collect(Collectors.toList()));
        }


        return socialCategoryMaster;
    }
}
