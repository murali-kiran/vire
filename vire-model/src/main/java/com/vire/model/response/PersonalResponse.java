package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.ProfileDto;
import com.vire.enumeration.Gender;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalResponse {

    private String profileId;
    private String name;
    //private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private Boolean isAadharVerified;
    private String dateOfBirth;
    private Gender gender;
    private String fileId;
    private Integer profileWeightage;
    private String profileType;
    private Long thumbsUpCount = 0l;
    private Long thumbsDownCount = 0l;
    private Long friendsCount = 0l;
    private Long starsCount = 0l;
    private List<ProfileSettingResponse> profileSettingTypes;
    private PersonalProfileResponse personalProfile;
    //private FirmProfileResponse firmProfile;

    public static PersonalResponse fromDto(final ProfileDto dto) {

        var personalResponse = new PersonalResponse();
        personalResponse.setProfileId(dto.getProfileId().toString());
        personalResponse.setName(dto.getName());
        //personalResponse.setPassword(dto.getPassword());
        personalResponse.setEmailId(dto.getEmailId());
        personalResponse.setMobileNumber(dto.getMobileNumber());
        personalResponse.setAadhar(dto.getAadhar());
        personalResponse.setIsAadharVerified(dto.getIsAadharVerified());
        personalResponse.setDateOfBirth(dto.getDateOfBirth());
        personalResponse.setGender(dto.getGender());
        personalResponse.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));
        personalResponse.setProfileWeightage(dto.getProfileWeightage());
        personalResponse.setProfileType(dto.getProfileType());

        var profileSettingResponse = dto.getProfileSettings().stream().map(profileSettingDto->ProfileSettingResponse.fromDto(profileSettingDto)).collect(Collectors.toList());
        personalResponse.setProfileSettingTypes(profileSettingResponse);

        personalResponse.setPersonalProfile(PersonalProfileResponse.fromDto(dto.getPersonalProfile()));


        return  personalResponse;
        //return new ModelMapper().map(dto, PersonalResponse.class);
    }
}
