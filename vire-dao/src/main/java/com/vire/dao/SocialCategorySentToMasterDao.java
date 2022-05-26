package com.vire.dao;

import com.vire.dto.SocialCategorySentToMasterDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="social_category_send_to_master")
@Data
public class SocialCategorySentToMasterDao {

    @Id
    @Column(name = "social_category_send_to_master_id")
    private Long socialCategorySentToMasterId;
    

    @Column(name = "category_send_to", nullable = false)
    private String categorySendTo;

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

    public SocialCategorySentToMasterDto toDto() {

        var dto = new SocialCategorySentToMasterDto();

        dto.setSocialCategorySentToMasterId(this.getSocialCategorySentToMasterId());
        
        dto.setCategorySendTo(this.getCategorySendTo());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static SocialCategorySentToMasterDao fromDto(final SocialCategorySentToMasterDto dto) {

        var socialCategorySentToMaster = new SocialCategorySentToMasterDao();

        socialCategorySentToMaster.setSocialCategorySentToMasterId(dto.getSocialCategorySentToMasterId());
        
        socialCategorySentToMaster.setCategorySendTo(dto.getCategorySendTo());

        return socialCategorySentToMaster;
    }
}
