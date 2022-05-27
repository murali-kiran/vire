package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.ProfileDto;
import com.vire.enumeration.Gender;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String dateOfBirth;
    private Gender gender;
    private String fileId;
    private String firstLogin;
    private Long thumbsUpCount = 0l;
    private Long thumbsDownCount = 0l;
    private Long friendsCount = 0l;
    private Long starsCount = 0l;

    private PersonalProfileResponse personalProfile;
    private FirmProfileResponse firmProfile;



    public static ProfileResponse fromDto(final ProfileDto dto) {

        var profileResponse = new ProfileResponse();
        profileResponse.setProfileId(dto.getProfileId().toString());
        profileResponse.setName(dto.getName());
        profileResponse.setPassword(dto.getPassword());
        profileResponse.setEmailId(dto.getEmailId());
        profileResponse.setMobileNumber(dto.getMobileNumber());
        profileResponse.setAadhar(dto.getAadhar());
        profileResponse.setIsAadharVerified(dto.getIsAadharVerified());
        profileResponse.setGender(dto.getGender());
        profileResponse.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));
        profileResponse.setDateOfBirth(dto.getDateOfBirth());
        profileResponse.setFirstLogin(dto.getFirstLogin());

        if(dto.getPersonalProfile()!=null){
            profileResponse.setPersonalProfile(PersonalProfileResponse.fromDto(dto.getPersonalProfile()));
        }else {
            profileResponse.setFirmProfile(FirmProfileResponse.fromDto(dto.getFirmProfile()));
        }

        return  profileResponse;
    }

}