package com.vire.dto;

import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;

@Data
public class FirmProfileDto {

    private Long firmProfileId;
    private String firmAddress;
    private AddressDto address;
    private String fieldOfBusiness;
    private String productOrService;

}
