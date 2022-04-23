package com.vire.model.response;

import com.vire.dto.AddressDto;
import lombok.Data;

@Data
public class AddressResponse {
    private String addressId;
    private String cityTownVillage;
    private String district;
    private String state;
    private Double latitude;
    private Double longitude;

    public static AddressResponse fromDto(final AddressDto dto){

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressId(dto.getAddressId().toString());
        addressResponse.setCityTownVillage(dto.getCityTownVillage());
        addressResponse.setDistrict(dto.getDistrict());
        addressResponse.setState(dto.getState());
        addressResponse.setLongitude(dto.getLongitude());
        addressResponse.setLatitude(dto.getLatitude());

        return  addressResponse;

    }
}
