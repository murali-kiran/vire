package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.ProfileDto;
import com.vire.enumeration.Gender;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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
    private Boolean isAadharVerified;
    private String dateOfBirth;
    private Gender gender;
    private String fileId;
    private Integer profileWeightage;
    private String profileType;
    private String firstLogin;
    private Long thumbsUpCount = 0l;
    private Long thumbsDownCount = 0l;
    private Long friendsCount = 0l;
    private Integer starsCount = 0;
    private List<ProfileSettingResponse> profileSettingTypes;
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
        profileResponse.setProfileWeightage(dto.getProfileWeightage());
        profileResponse.setProfileType(dto.getProfileType());
        var profileSettingResponse = dto.getProfileSettings().stream().map(profileSettingDto->ProfileSettingResponse.fromDto(profileSettingDto)).collect(Collectors.toList());
        profileResponse.setProfileSettingTypes(profileSettingResponse);

        if(dto.getPersonalProfile()!=null){
            profileResponse.setPersonalProfile(PersonalProfileResponse.fromDto(dto.getPersonalProfile()));
        }else {
            profileResponse.setFirmProfile(FirmProfileResponse.fromDto(dto.getFirmProfile()));
        }

        return  profileResponse;
    }

}