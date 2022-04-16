package com.vire.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AddressDto {

    private Long addressId;

    private String cityTownVillage;

    private String district;

    private String state;

    private Double latitude;

    private Double longitude;

    public Long createdTime;

    public Long updatedTime;
}
