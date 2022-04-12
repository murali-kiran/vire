package com.vire.model.response;

import lombok.Data;

@Data
public class AddressResponse {
    private Long addressId;
    private String cityTownVillage;
    private String district;
    private String state;
    private Double latitude;
    private Double longitude;
}
