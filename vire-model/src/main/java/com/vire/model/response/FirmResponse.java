package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
public class FirmResponse {

    private String profileId;
    private String name;
    //private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private String fileId;
    private Long thumbsUpCount;
    private Long thumbsDownCount;
    private Long friendsCount;
    private Long starsCount;
    private FirmProfileResponse firmProfile;

    public static FirmResponse fromDto(final ProfileDto dto) {

        FirmResponse firmResponse = new FirmResponse();
        firmResponse.setProfileId(dto.getProfileId().toString());
        firmResponse.setName(dto.getName());
        //firmResponse.setPassword(dto.getPassword());
        firmResponse.setEmailId(dto.getEmailId());
        firmResponse.setMobileNumber(dto.getMobileNumber());
        firmResponse.setAadhar(dto.getAadhar());
        firmResponse.setIsAadharVerified(dto.getIsAadharVerified());
        firmResponse.setFirmProfile(FirmProfileResponse.fromDto(dto.getFirmProfile()));
        firmResponse.setFileId(String.valueOf(dto.getFileId()));
        return  firmResponse;
        //return new ModelMapper().map(dto, FirmResponse.class);
    }
}
