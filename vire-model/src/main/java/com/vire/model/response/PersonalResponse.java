package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
public class PersonalResponse {

    private String profileId;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileResponse personalProfile;
    //private FirmProfileResponse firmProfile;

    public static PersonalResponse fromDto(final ProfileDto dto) {

        var personalResponse = new PersonalResponse();
        personalResponse.setProfileId(dto.getProfileId().toString());
        personalResponse.setName(dto.getName());
        personalResponse.setPassword(dto.getPassword());
        personalResponse.setEmailId(dto.getEmailId());
        personalResponse.setMobileNumber(dto.getMobileNumber());
        personalResponse.setAadhar(dto.getAadhar());
        personalResponse.setIsAadharVerified(dto.getIsAadharVerified());
        personalResponse.setPersonalProfile(PersonalProfileResponse.fromDto(dto.getPersonalProfile()));

        return  personalResponse;
        //return new ModelMapper().map(dto, PersonalResponse.class);
    }
}
