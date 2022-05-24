package com.vire.model.response;

import com.vire.dto.ProfileThumbsUpDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileThumbsUpResponse {

    private String profileThumbsUpId;
    
    private Long profileId;
    private Long thumbsUpBy;
    private String reason;
    private String description;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileThumbsUpResponse fromDto(final ProfileThumbsUpDto dto) {

        var profileThumbsUp = new ProfileThumbsUpResponse();

        profileThumbsUp.setProfileThumbsUpId(String.valueOf(dto.getProfileThumbsUpId()));
        profileThumbsUp.setProfileId(dto.getProfileId());
        profileThumbsUp.setThumbsUpBy(dto.getThumbsUpBy());
        profileThumbsUp.setReason(dto.getReason());
        profileThumbsUp.setDescription(dto.getDescription());
        profileThumbsUp.setCreatedTime(dto.getCreatedTime());
        profileThumbsUp.setUpdatedTime(dto.getUpdatedTime());

        return profileThumbsUp;
    }
}
