package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class LocationMasterDto {
    private Long locationMasterId;
    private String country;
    private String state;
    private String district;
    private String city;
    public Long createdTime;
    public Long updatedTime;
}
