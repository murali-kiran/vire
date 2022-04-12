package com.vire.model.response;

import com.vire.dto.AddressDto;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;

@Data
public class FirmProfileResponse {
    private Long firmProfileId;
    private String firmAddress;
    private AddressDto address;
    private FieldOfBusiness fieldOfBusiness;
    private ProductOrService productOrService;
}
