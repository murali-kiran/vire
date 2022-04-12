package com.vire.model.request;

import com.vire.dto.AddressDto;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;

@Data
public class FirmProfileRequest {
    private Long firmProfileId;
    private String firmAddress;
    private AddressDto address;
    private FieldOfBusiness fieldOfBusiness;
    private ProductOrService productOrService;
}
