package com.vire.dao;

import com.vire.dto.FirmProfileDto;
import com.vire.dto.PersonalProfileDto;
import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "firm_profile")
@Data
public class FirmProfileDao  {

    @Id
    @Column(name = "firm_profile_id")
    private Long firmProfileId;

    @Column(name = "firm_address", nullable = true)
    private String firmAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "firm_address_id")
    private AddressDao address;

    @Column(name = "field_of_business", nullable = false)
    //@Enumerated(EnumType.STRING)
    private String fieldOfBusiness;

    @Column(name = "product_or_service", nullable = false)
    //@Enumerated(EnumType.STRING)
    private String productOrService;

    @OneToOne
    @MapsId
    @JoinColumn(name = "firm_profile_id")
    private ProfileDao profile;

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

    public FirmProfileDto toDto() {
        return new ModelMapper().map(this, FirmProfileDto.class);
    }

    public static FirmProfileDao fromDto(final FirmProfileDto dto) {
        return new ModelMapper().map(dto, FirmProfileDao.class);
    }
}
