package com.vire.model.request;

import com.vire.dto.AddressDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AddressRequest {
    private String addressId;
    private String cityTownVillage;
    private String district;
    private String state;
    private Double latitude;
    private Double longitude;

    public AddressDto toDto() {

        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId((this.addressId==null || !StringUtils.isNumeric(this.addressId))?null:Long.valueOf(this.addressId));
        addressDto.setCityTownVillage(this.cityTownVillage);
        addressDto.setDistrict(this.district);
        addressDto.setState(this.state);
        addressDto.setLongitude(this.longitude);
        addressDto.setLatitude(this.latitude);

        return  addressDto;
    }
}
