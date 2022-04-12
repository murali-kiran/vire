package com.vire.model.request;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class FirmRequest {

    private Long profileId;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private FirmProfileRequest firmProfile;

    public ProfileDto toDto() {
        return  new ModelMapper().map(this,ProfileDto.class);
    }
}
