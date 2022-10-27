package com.vire.dto;

import com.vire.enumeration.Gender;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private Long profileId;
    private String name;
    private Long userId;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private Boolean isAadharVerified;
    private Boolean isBlocked;
    private String dateOfBirth;
    private Gender gender;
    private Long fileId;
    private String firstLogin;
    private Integer profileWeightage;
    private String profileType;
    private List<ProfileSettingDto> profileSettings;
    private PersonalProfileDto personalProfile;
    private FirmProfileDto firmProfile;
    public String profileStatus;
}
