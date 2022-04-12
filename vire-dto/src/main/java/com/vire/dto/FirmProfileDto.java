package com.vire.dto;

import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;

@Data
public class FirmProfileDto {

    private Long firmProfileId;
    private String firmAddress;
    private AddressDto address;
    private FieldOfBusiness fieldOfBusiness;
    private ProductOrService productOrService;

}
