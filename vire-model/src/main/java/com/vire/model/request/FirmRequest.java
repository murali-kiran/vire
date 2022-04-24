package com.vire.model.request;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FirmRequest {

    private String profileId;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private FirmProfileRequest firmProfile;

    public ProfileDto toDto() {

        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileId((this.getProfileId() == null  || !StringUtils.isNumeric(this.getProfileId()))? null : Long.valueOf(this.getProfileId()));
        profileDto.setName(this.name);
        profileDto.setPassword(this.password);
        profileDto.setEmailId(this.emailId);
        profileDto.setMobileNumber(this.mobileNumber);
        profileDto.setAadhar(this.aadhar);
        profileDto.setIsAadharVerified(this.isAadharVerified);
        profileDto.setFirmProfile(this.getFirmProfile().toDto());

        return profileDto;

        //return  new ModelMapper().map(this,ProfileDto.class);
    }
}
