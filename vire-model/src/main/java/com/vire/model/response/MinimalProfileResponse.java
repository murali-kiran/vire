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


    public static MinimalProfileResponse fromDto(final ProfileDto dto) {

        var profileResponse = new MinimalProfileResponse();
        profileResponse.setProfileId(dto.getProfileId().toString());
        profileResponse.setName(dto.getName());
        profileResponse.setProfileType(dto.getProfileType());
        profileResponse.setFileId(dto.getFileId() == null ? null : String.valueOf(dto.getFileId()));

        return profileResponse;
    }

    public void cloneProperties(MinimalProfileResponse response){
        this.setProfileId(response.getProfileId());
        this.setName(response.getName());
        this.setFileId(response.getFileId());
        this.setProfileType(response.getProfileType());
    }
}