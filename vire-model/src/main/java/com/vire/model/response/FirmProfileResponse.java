package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.FirmProfileDto;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirmProfileResponse {
    private String firmProfileId;
    private String firmAddress;
    private AddressResponse address;
    private String fieldOfBusiness;
    private String productOrService;

    public static FirmProfileResponse fromDto(FirmProfileDto dto){

        FirmProfileResponse firmProfileResponse = new FirmProfileResponse();
        firmProfileResponse.setFirmProfileId(dto.getFirmProfileId().toString());
        firmProfileResponse.setFirmAddress(dto.getFirmAddress());

        if(dto.getAddress()!=null)
        firmProfileResponse.setAddress(AddressResponse.fromDto(dto.getAddress()));

        firmProfileResponse.setFieldOfBusiness(dto.getFieldOfBusiness());
        firmProfileResponse.setProductOrService(dto.getProductOrService());
        return firmProfileResponse;

    }
}
