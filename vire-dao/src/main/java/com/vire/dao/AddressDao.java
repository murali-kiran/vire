package com.vire.dao;

import com.vire.dto.AddressDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "address")
@Data
public class AddressDao {

    @Id
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "village_town_city", nullable = false)
    private String cityTownVillage;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    @Column(name = "created_time", nullable = false, updatable = false)
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

    public AddressDto toDto() {
        return new ModelMapper().map(this, AddressDto.class);
    }

    public static AddressDao fromDto(final AddressDto dto) {
        return new ModelMapper().map(dto, AddressDao.class);
    }

}
