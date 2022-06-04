package com.vire.dao;

import com.vire.dto.LocationMasterDto;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="location_master")
@Data
public class LocationMasterDao {

    @Id
    @Column(name = "location_master_id")
    private Long locationMasterId;
    

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "city", nullable = false)
    private String city;

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

    public LocationMasterDto toDto() {

        var dto = new LocationMasterDto();

        dto.setLocationMasterId(this.getLocationMasterId());
        
        dto.setCountry(this.getCountry());
        dto.setState(this.getState());
        dto.setDistrict(this.getDistrict());
        dto.setCity(this.getCity());

        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());

        return dto;
    }

    public static LocationMasterDao fromDto(final LocationMasterDto dto) {

        var locationMaster = new LocationMasterDao();

        locationMaster.setLocationMasterId(dto.getLocationMasterId());
        
        locationMaster.setCountry(dto.getCountry());
        locationMaster.setState(dto.getState());
        locationMaster.setDistrict(dto.getDistrict());
        locationMaster.setCity(dto.getCity());

        return locationMaster;
    }
}
