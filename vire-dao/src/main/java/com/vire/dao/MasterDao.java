package com.vire.dao;

import com.vire.dto.MasterDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="master")
@Data
public class MasterDao {

    @Id
    @Column(name = "master_id")
    private Long masterId;
    

    @Column(name = "master_type", nullable = false)
    private String masterType;

    @Column(name = "master_value", nullable = false)
    private String masterValue;

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

    public MasterDto toDto() {

        var dto = new MasterDto();

        dto.setMasterId(this.getMasterId());
        
        dto.setMasterType(this.getMasterType());
        dto.setMasterValue(this.getMasterValue());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static MasterDao fromDto(final MasterDto dto) {

        var master = new MasterDao();

        master.setMasterId(dto.getMasterId());
        
        master.setMasterType(dto.getMasterType());
        master.setMasterValue(dto.getMasterValue());

        return master;
    }
}
