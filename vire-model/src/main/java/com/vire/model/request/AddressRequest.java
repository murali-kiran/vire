package com.vire.model.request;

import lombok.Data;

@Data
public class AddressRequest {
    private Long addressId;
    private String cityTownVillage;
    private String district;
    private String state;
    private Double latitude;
    private Double longitude;
}
