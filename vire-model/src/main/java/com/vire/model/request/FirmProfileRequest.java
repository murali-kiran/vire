package com.vire.model.request;

import com.vire.dto.FirmProfileDto;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FirmProfileRequest {
    private String firmProfileId;
    private String firmAddress;
    private AddressRequest address;
    private FieldOfBusiness fieldOfBusiness;
    private ProductOrService productOrService;

    public FirmProfileDto toDto() {

        FirmProfileDto firmProfileDto = new FirmProfileDto();
        firmProfileDto.setFirmProfileId((this.getFirmProfileId() == null  || !StringUtils.isNumeric(this.getFirmProfileId()))? null : Long.valueOf(this.getFirmProfileId()));
        firmProfileDto.setFirmAddress(this.getFirmAddress());
        firmProfileDto.setAddress(this.getAddress().toDto());
        firmProfileDto.setFieldOfBusiness(this.getFieldOfBusiness());
        firmProfileDto.setProductOrService(this.getProductOrService());

        return firmProfileDto;
    }

}
