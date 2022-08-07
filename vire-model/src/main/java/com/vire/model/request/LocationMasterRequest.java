package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.LocationMasterDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LocationMasterRequest {

    private String locationMasterId;
    
    private String country;
    private String state;
    private String district;
    private String city;
    private Integer pincode;
    private Double latitude;
    private Double longitude;

    public LocationMasterDto toDto(Snowflake snowflake) {

        var dto = new LocationMasterDto();

        if(snowflake == null) {
            dto.setLocationMasterId(this.getLocationMasterId() == null ? null : Long.valueOf(this.getLocationMasterId()));
        }else {
            dto.setLocationMasterId(snowflake.nextId());
        }
        
        dto.setCountry(this.getCountry());
        dto.setState(this.getState());
        dto.setDistrict(this.getDistrict());
        dto.setCity(this.getCity());
        dto.setPincode(this.getPincode());
        dto.setLatitude(this.getLatitude());
        dto.setLongitude(this.getLongitude());

        return dto;
    }

    public LocationMasterDto toDto() {
        return toDto(null);
    }
}