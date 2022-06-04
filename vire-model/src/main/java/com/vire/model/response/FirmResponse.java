package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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
    private Integer profileWeightage;
    private Long thumbsUpCount = 0l;
    private Long thumbsDownCount = 0l;
    private Long friendsCount = 0l;
    private Long starsCount = 0l;
    private List<ProfileSettingResponse> profileSettingTypes;
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
        firmResponse.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));
        firmResponse.setProfileWeightage(dto.getProfileWeightage());

        var profileSettingResponse = dto.getProfileSettings().stream().map(profileSettingDto->ProfileSettingResponse.fromDto(profileSettingDto)).collect(Collectors.toList());
        firmResponse.setProfileSettingTypes(profileSettingResponse);

        return firmResponse;
        //return new ModelMapper().map(dto, FirmResponse.class);
    }
}
