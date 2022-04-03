package com.vire.model.request;

import com.vire.dto.PersonalProfileDto;
import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ProfileRequest {

    private Long id;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileRequest personalProfile;

    public ProfileDto toDto() {
        return  new ModelMapper().map(this,ProfileDto.class);
    }
}
