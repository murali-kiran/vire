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
    private String isAadharVerified;
    private String dateOfBirth;
    private Gender gender;
    private Long fileId;
    private String firstLogin;
    private Integer profileWeightage;
    private List<ProfileSettingDto> profileSettings;
    private PersonalProfileDto personalProfile;
    private FirmProfileDto firmProfile;
}
