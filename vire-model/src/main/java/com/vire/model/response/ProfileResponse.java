package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
public class ProfileResponse {

    private String token;
    private String type = "Bearer";
    private String profileId;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileResponse personalProfile;
    private FirmProfileResponse firmProfile;

    /*
    public static ProfileResponse fromDto(final ProfileDto dto){

        var loginResponse = new ProfileResponse();
        loginResponse.setProfileId(dto.getProfileId().toString());
        loginResponse.setAadhar(dto.getAadhar());
        loginResponse.setEmailId(dto.getEmailId());
        loginResponse.setName(dto.getName());
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

     */

    public static ProfileResponse fromDto(final ProfileDto dto) {

        var profileResponse = new ProfileResponse();
        profileResponse.setProfileId(dto.getProfileId().toString());
        profileResponse.setName(dto.getName());
        profileResponse.setPassword(dto.getPassword());
        profileResponse.setEmailId(dto.getEmailId());
        profileResponse.setMobileNumber(dto.getMobileNumber());
        profileResponse.setAadhar(dto.getAadhar());
        profileResponse.setIsAadharVerified(dto.getIsAadharVerified());

        if(dto.getPersonalProfile()!=null){
            profileResponse.setPersonalProfile(PersonalProfileResponse.fromDto(dto.getPersonalProfile()));
        }else {
            profileResponse.setFirmProfile(FirmProfileResponse.fromDto(dto.getFirmProfile()));
        }

        return  profileResponse;
    }

}
