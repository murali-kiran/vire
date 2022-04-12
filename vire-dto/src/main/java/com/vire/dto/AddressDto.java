package com.vire.dto;

import lombok.Data;

@Data
public class AddressDto {

    private Long addressId;

    private String cityTownVillage;

    private String district;

    private String state;

    private Double latitude;

    private Double longitude;
}
