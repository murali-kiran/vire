package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinimalProfileResponse {

    private String profileId;
    private String name;
    private String fileId;
    private String profileType;
    private String location;
    private Integer profileWeightage;

    private String thumbsUp;
    private String thumbsDown;


    public static MinimalProfileResponse fromDto(final ProfileDto dto) {

        var profileResponse = new MinimalProfileResponse();
        profileResponse.setProfileId(dto.getProfileId().toString());
        profileResponse.setName(dto.getName());
        profileResponse.setProfileType(dto.getProfileType());
        profileResponse.setProfileWeightage(dto.getProfileWeightage());
        profileResponse.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));
        if(dto.getPersonalProfile() == null && dto.getFirmProfile() == null){
            profileResponse.setLocation(null);
        }else{
            profileResponse.setLocation(dto.getPersonalProfile() != null ? dto.getPersonalProfile().getPresentAddress().getCityTownVillage():dto.getFirmProfile().getAddress().getCityTownVillage());
        }

        return profileResponse;
    }

    public void cloneProperties(MinimalProfileResponse response){
        this.setProfileId(response.getProfileId());
        this.setName(response.getName());
        this.setFileId(response.getFileId());
        this.setProfileType(response.getProfileType());
        this.setLocation(response.getLocation());
        this.setThumbsDown(response.getThumbsDown());
        this.setThumbsUp(response.getThumbsUp());
        this.setProfileWeightage(response.getProfileWeightage());
    }
}
