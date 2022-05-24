package com.vire.dao;

import com.vire.dto.MasterDetailsDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="master_details")
@Data
public class MasterDetailsDao {

    @Id
    @Column(name = "master_details_id")
    private Long masterDetailsId;
    

    @Column(name = "master_id", nullable = false)
    private Long masterId;

    @Column(name = "detail_type", nullable = false)
    private String detailType;

    @Column(name = "detail_value", nullable = false)
    private String detailValue;

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

    public MasterDetailsDto toDto() {

        var dto = new MasterDetailsDto();

        dto.setMasterDetailsId(this.getMasterDetailsId());
        
        dto.setMasterId(this.getMasterId());
        dto.setDetailType(this.getDetailType());
        dto.setDetailValue(this.getDetailValue());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static MasterDetailsDao fromDto(final MasterDetailsDto dto) {

        var masterDetails = new MasterDetailsDao();

        masterDetails.setMasterDetailsId(dto.getMasterDetailsId());
        
        masterDetails.setMasterId(dto.getMasterId());
        masterDetails.setDetailType(dto.getDetailType());
        masterDetails.setDetailValue(dto.getDetailValue());

        return masterDetails;
    }
}
