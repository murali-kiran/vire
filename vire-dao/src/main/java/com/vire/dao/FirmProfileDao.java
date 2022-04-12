package com.vire.dao;

import com.vire.dto.FirmProfileDto;
import com.vire.dto.PersonalProfileDto;
import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name = "firm_profile")
@Data
public class FirmProfileDao {

        @Id
        @Column(name = "profile_id")
        private Long firmProfileId;

        @Column(name = "firm_address", nullable = true)
        private String firmAddress;

        @ManyToOne(fetch = FetchType.LAZY,optional=false)
        @JoinColumn(name = "firm_address_id")
        private AddressDao address;

        @Column(name = "field_of_business", nullable = false)
        @Enumerated(EnumType.STRING)
        private FieldOfBusiness fieldOfBusiness;

        @Column(name = "product_or_service", nullable = false)
        @Enumerated(EnumType.STRING)
        private ProductOrService productOrService;

        @OneToOne
        @MapsId
        @JoinColumn(name = "profile_id")
        private ProfileDao profile;

        public FirmProfileDto toDto() {
                return new ModelMapper().map(this,FirmProfileDto.class);
        }

        public static FirmProfileDao fromDto(final FirmProfileDto dto) {
                return new ModelMapper().map(dto, FirmProfileDao.class);
        }
}
