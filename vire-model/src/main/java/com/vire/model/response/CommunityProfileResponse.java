package com.vire.model.response;

import com.vire.dto.CommunityProfileDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityProfileResponse {

    private String communityProfileId;
    private String communityId;
    private String profileId;
    private String status;
    private Boolean isAdmin;
    private Long createdTime;
    private Long updatedTime;

    public static CommunityProfileResponse fromDto(final CommunityProfileDto dto) {

        var communityProfile = new CommunityProfileResponse();

        communityProfile.setCommunityProfileId(String.valueOf(dto.getCommunityProfileId()));
        communityProfile.setCommunityId(String.valueOf(dto.getCommunityId()));
        communityProfile.setProfileId(String.valueOf(dto.getProfileId()));
        communityProfile.setStatus(dto.getStatus());
        communityProfile.setIsAdmin(dto.getIsAdmin());
        communityProfile.setCreatedTime(dto.getCreatedTime());
        communityProfile.setUpdatedTime(dto.getUpdatedTime());

        return communityProfile;
    }
}
