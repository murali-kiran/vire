package com.vire.model.response;

import com.vire.dto.ProfileThumbsUpDto;
import lombok.Data;

@Data
public class ProfileThumbsUpResponse {

    private String profileThumbsUpId;
    
    private String profileId;
    private String thumbsUpBy;
    private String reason;
    private String description;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileThumbsUpResponse fromDto(final ProfileThumbsUpDto dto) {

        var profileThumbsUp = new ProfileThumbsUpResponse();

        profileThumbsUp.setProfileThumbsUpId(String.valueOf(dto.getProfileThumbsUpId()));
        profileThumbsUp.setProfileId(dto.getProfileId() == null ? null : String.valueOf(dto.getProfileId()));
        profileThumbsUp.setThumbsUpBy(dto.getThumbsUpBy() == null ? null : String.valueOf(dto.getThumbsUpBy()));
        profileThumbsUp.setReason(dto.getReason());
        profileThumbsUp.setDescription(dto.getDescription());
        profileThumbsUp.setCreatedTime(dto.getCreatedTime());
        profileThumbsUp.setUpdatedTime(dto.getUpdatedTime());

        return profileThumbsUp;
    }
}
