package com.vire.model.request;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
public class PersonalRequest {

    private String profileId;
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Password required")
    private String password;
    @NotBlank(message = "Profile required")
    @Pattern(regexp="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
            "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Invalid email address")
    private String emailId;
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be numeric and 10 digits")
    private String mobileNumber;
    @Pattern(regexp="(^$|[0-9]{12})", message = "Aadhar must be numeric and 12 digits")
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileRequest personalProfile;

    public ProfileDto toDto() {

        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileId(this.getProfileId() == null || !StringUtils.isNumeric(this.getProfileId())? null : Long.valueOf(this.getProfileId()));
        profileDto.setName(this.name);
        profileDto.setPassword(this.password);
        profileDto.setEmailId(this.emailId);
        profileDto.setMobileNumber(this.mobileNumber);
        profileDto.setAadhar(this.aadhar);
        profileDto.setIsAadharVerified(this.isAadharVerified);
        profileDto.setPersonalProfile(this.personalProfile.toDto());

        return profileDto;

        //return  new ModelMapper().map(this,ProfileDto.class);
    }
}
