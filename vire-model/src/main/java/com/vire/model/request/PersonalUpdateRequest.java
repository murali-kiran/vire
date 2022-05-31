package com.vire.model.request;

import com.vire.dto.ProfileDto;
import com.vire.dto.ProfileSettingDto;
import com.vire.enumeration.Gender;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Data
public class PersonalUpdateRequest {

    private String profileId;
    @NotBlank(message = "Name required")
    private String name;
    //@NotBlank(message = "Password required")
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

    @Pattern(regexp="(^[0-9]*$)", message = "File id must be numeric")
    private String fileId;

    @NotBlank(message = "Date of birth required dd-MM-YYYY format")
    // date range years from 1800 to 2999
    @Pattern(regexp="^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((18|19|2[0-9])[0-9]{2})$", message = "Invalid date of birth format")
    private String dateOfBirth;
    private Gender gender;

    @Valid
    private PersonalProfileRequest personalProfile;

    private List<ProfileSettingRequest> profileSettingTypes;

    public ProfileDto toDto() {

        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileId(this.getProfileId() == null || !StringUtils.isNumeric(this.getProfileId())? null : Long.valueOf(this.getProfileId()));
        profileDto.setName(this.name);
        profileDto.setPassword(this.password);
        profileDto.setEmailId(this.emailId);
        profileDto.setMobileNumber(this.mobileNumber);
        profileDto.setAadhar(this.aadhar);
        profileDto.setIsAadharVerified(this.isAadharVerified);
        profileDto.setDateOfBirth(this.dateOfBirth);
        profileDto.setGender(this.gender);
        profileDto.setFileId(this.getFileId() == null || !StringUtils.isNumeric(this.getFileId())? null : Long.valueOf(this.getFileId()));
        profileDto.setPersonalProfile(this.personalProfile.toDto());

        if(this.getProfileSettingTypes()!=null && !this.getProfileSettingTypes().isEmpty()){
            var profileSettingsDtos = new ArrayList<ProfileSettingDto>();
            for(var profileSetting : this.getProfileSettingTypes()){
                ProfileSettingDto profileSettingDto = new ProfileSettingDto();
                profileSettingDto.setProfileSettingId(Long.valueOf(profileSetting.getProfileSettingId()));
                profileSettingDto.setSettingType(profileSetting.getSettingType());
                profileSettingDto.setIsEnable(profileSetting.getIsEnable());
                profileSettingsDtos.add(profileSettingDto);
            }
            profileDto.setProfileSettings(profileSettingsDtos);
        }

        return profileDto;

        //return  new ModelMapper().map(this,ProfileDto.class);
    }
}