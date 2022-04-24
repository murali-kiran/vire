package com.vire.model.response;

import com.vire.dto.FirmProfileDto;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;

@Data
public class FirmProfileResponse {
    private String firmProfileId;
    private String firmAddress;
    private AddressResponse address;
    private FieldOfBusiness fieldOfBusiness;
    private ProductOrService productOrService;

    public static FirmProfileResponse fromDto(FirmProfileDto dto){

        FirmProfileResponse firmProfileResponse = new FirmProfileResponse();
        firmProfileResponse.setFirmProfileId(dto.getFirmProfileId().toString());
        firmProfileResponse.setFirmAddress(dto.getFirmAddress());
        firmProfileResponse.setAddress(AddressResponse.fromDto(dto.getAddress()));
        firmProfileResponse.setFieldOfBusiness(dto.getFieldOfBusiness());
        firmProfileResponse.setProductOrService(dto.getProductOrService());
        return firmProfileResponse;

    }
}
