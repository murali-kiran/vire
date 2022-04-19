package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class LoginResponse {

    private Long profileId;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileResponse personalProfile;
    private FirmProfileResponse firmProfile;

    public static LoginResponse fromDto(final ProfileDto dto){

        var loginResponse = new LoginResponse();
        loginResponse.setAadhar(dto.getAadhar());
        loginResponse.setEmailId(dto.getEmailId());
        loginResponse.setName(dto.getName());
        loginResponse.setProfileId(dto.getProfileId());
        loginResponse.setMobileNumber(dto.getMobileNumber());

        if(dto.getPersonalProfile()!=null){
            PersonalProfileResponse personalProfileResponse = new ModelMapper().map(dto.getPersonalProfile(), PersonalProfileResponse.class);
            loginResponse.setPersonalProfile(personalProfileResponse);
        }else {
            FirmProfileResponse firmProfileResponse = new ModelMapper().map(dto.getFirmProfile(), FirmProfileResponse.class);
            loginResponse.setFirmProfile(firmProfileResponse);
        }

        return loginResponse;
    }

}
