package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
public class FirmResponse {

    private String profileId;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private FirmProfileResponse firmProfile;

    public static FirmResponse fromDto(final ProfileDto dto) {

        FirmResponse firmResponse = new FirmResponse();
        firmResponse.setProfileId(dto.getProfileId().toString());
        firmResponse.setName(dto.getName());
        firmResponse.setPassword(dto.getPassword());
        firmResponse.setEmailId(dto.getEmailId());
        firmResponse.setMobileNumber(dto.getMobileNumber());
        firmResponse.setAadhar(dto.getAadhar());
        firmResponse.setIsAadharVerified(dto.getIsAadharVerified());
        firmResponse.setFirmProfile(FirmProfileResponse.fromDto(dto.getFirmProfile()));

        return  firmResponse;
        //return new ModelMapper().map(dto, FirmResponse.class);
    }
}
