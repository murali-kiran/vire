package com.vire.model.response;

import com.vire.dto.ProfileDto;
import com.vire.enumeration.Gender;
import lombok.Data;

@Data
public class PersonalResponse {

    private String profileId;
    private String name;
    //private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private String dateOfBirth;
    private Gender gender;
    private String fileId;
    private Long thumbsUpCount;
    private Long thumbsDownCount;
    private Long friendsCount;
    private Long starsCount;
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
        personalResponse.setFileId(String.valueOf(dto.getFileId()));
        personalResponse.setPersonalProfile(PersonalProfileResponse.fromDto(dto.getPersonalProfile()));


        return  personalResponse;
        //return new ModelMapper().map(dto, PersonalResponse.class);
    }
}
