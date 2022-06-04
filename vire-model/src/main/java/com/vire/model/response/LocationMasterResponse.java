package com.vire.model.response;

import com.vire.dto.LocationMasterDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LocationMasterResponse {

    private String locationMasterId;
    
    private String country;
    private String state;
    private String district;
    private String city;
    private Long createdTime;
    private Long updatedTime;

    public static LocationMasterResponse fromDto(final LocationMasterDto dto) {

        var locationMaster = new LocationMasterResponse();

        locationMaster.setLocationMasterId(String.valueOf(dto.getLocationMasterId()));
        locationMaster.setCountry(dto.getCountry());
        locationMaster.setState(dto.getState());
        locationMaster.setDistrict(dto.getDistrict());
        locationMaster.setCity(dto.getCity());
        locationMaster.setCreatedTime(dto.getCreatedTime());
        locationMaster.setUpdatedTime(dto.getUpdatedTime());

        return locationMaster;
    }
}
