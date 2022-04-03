package com.vire.model.request;

import lombok.Data;

@Data
public class AddressRequest {
    private Long id;
    private String cityTownVillage;
    private String district;
    private String state;
    private Double latitude;
    private Double longitude;
}
