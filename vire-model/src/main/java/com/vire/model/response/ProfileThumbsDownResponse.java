package com.vire.model.response;

import com.vire.dto.ProfileThumbsDownDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileThumbsDownResponse {

    private String profileThumbsDownId;
    
    private Long profileId;
    private Long thumbsDownBy;
    private String reason;
    private String description;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileThumbsDownResponse fromDto(final ProfileThumbsDownDto dto) {

        var profileThumbsDown = new ProfileThumbsDownResponse();

        profileThumbsDown.setProfileThumbsDownId(String.valueOf(dto.getProfileThumbsDownId()));
        profileThumbsDown.setProfileId(dto.getProfileId());
        profileThumbsDown.setThumbsDownBy(dto.getThumbsDownBy());
        profileThumbsDown.setReason(dto.getReason());
        profileThumbsDown.setDescription(dto.getDescription());
        profileThumbsDown.setCreatedTime(dto.getCreatedTime());
        profileThumbsDown.setUpdatedTime(dto.getUpdatedTime());

        return profileThumbsDown;
    }
}
