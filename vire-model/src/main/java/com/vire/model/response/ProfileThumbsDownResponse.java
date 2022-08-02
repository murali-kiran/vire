package com.vire.model.response;

import com.vire.dto.ProfileThumbsDownDto;
import com.vire.utils.Utility;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
public class ProfileThumbsDownResponse {

    private String profileThumbsDownId;
    private String profileId;
    private String thumbsDownBy;
    private String reason;
    private String description;
    private MinimalProfileResponse thumbsDownProfile;
    private String createdTime;

    public static ProfileThumbsDownResponse fromDto(final ProfileThumbsDownDto dto) {

        var profileThumbsDown = new ProfileThumbsDownResponse();

        profileThumbsDown.setProfileThumbsDownId(String.valueOf(dto.getProfileThumbsDownId()));
        profileThumbsDown.setProfileId(dto.getProfileId() == null ? null : String.valueOf(dto.getProfileId()));
        profileThumbsDown.setThumbsDownBy(dto.getThumbsDownBy() == null ? null : String.valueOf(dto.getThumbsDownBy()));
        profileThumbsDown.setReason(dto.getReason());
        profileThumbsDown.setDescription(dto.getDescription());
        if (dto.getProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getThumbsDownBy()));
            profileThumbsDown.setThumbsDownProfile(minProfileRes);
        }
        profileThumbsDown.setCreatedTime(Utility.customTimeFormat(new Date(dto.getCreatedTime())));

        return profileThumbsDown;
    }
}
