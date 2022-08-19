package com.vire.model.response;

import com.vire.dto.ProfileThumbsUpDto;
import com.vire.utils.Utility;
import lombok.Data;

import java.util.Date;

@Data
public class ProfileThumbsUpResponse {

    private String profileThumbsUpId;
    
    private String profileId;
    private String thumbsUpBy;
    private String reason;
    private String description;
    private MinimalProfileResponse thumbsUpProfile;
    private String createdTime;

    public static ProfileThumbsUpResponse fromDto(final ProfileThumbsUpDto dto) {

        var profileThumbsUp = new ProfileThumbsUpResponse();

        profileThumbsUp.setProfileThumbsUpId(String.valueOf(dto.getProfileThumbsUpId()));
        profileThumbsUp.setProfileId(dto.getProfileId() == null ? null : String.valueOf(dto.getProfileId()));
        profileThumbsUp.setThumbsUpBy(dto.getThumbsUpBy() == null ? null : String.valueOf(dto.getThumbsUpBy()));
        profileThumbsUp.setReason(dto.getReason());
        profileThumbsUp.setDescription(dto.getDescription());
        if (dto.getProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getThumbsUpBy()));
            profileThumbsUp.setThumbsUpProfile(minProfileRes);
        }
        profileThumbsUp.setCreatedTime(Utility.customTimeFormat(dto.getCreatedTime()));

        return profileThumbsUp;
    }

}
