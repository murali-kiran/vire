package com.vire.dto;

import lombok.Data;

@Data
public class AddressDto {

    private Long id;

    private String cityTownVillage;

    private String district;

    private String state;

    private Double latitude;

    private Double longitude;
}
