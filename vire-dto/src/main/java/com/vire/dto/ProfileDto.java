package com.vire.dto;

import lombok.Data;

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
    //private Boolean isPersonalProfile;
    private PersonalProfileDto personalProfile;
    private FirmProfileDto firmProfile;
}
