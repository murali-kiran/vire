package com.vire.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private Long id;
    private String name;
    private Long userId;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileDto personalProfile;
}
