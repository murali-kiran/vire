package com.vire.model.response;

import com.vire.dto.CommunityDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityResponse {

    private String communityId;

    private String name;
    private String description;
    private MinimalProfileResponse creatorProfile;
    private String fileId;
    private String rules;
    private Boolean memberProofRequired;
    private List<CommunityProfileResponse> communityProfiles;
    private Long createdTime;
    private Long updatedTime;

    public static CommunityResponse fromDto(final CommunityDto dto) {

        var community = new CommunityResponse();

        community.setCommunityId(String.valueOf(dto.getCommunityId()));
        community.setName(dto.getName());
        community.setDescription(dto.getDescription());

        if (dto.getCreatorProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getCreatorProfileId()));
            community.setCreatorProfile(minProfileRes);
        }

        community.setFileId(String.valueOf(dto.getFileId()));
        community.setRules(dto.getRules());
        community.setMemberProofRequired(dto.getMemberProofRequired());

        if (dto.getCommunityProfiles() != null && !dto.getCommunityProfiles().isEmpty()) {
            community.setCommunityProfiles(dto.getCommunityProfiles()
                    .stream()
                    .map(child -> CommunityProfileResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }

        community.setCreatedTime(dto.getCreatedTime());
        community.setUpdatedTime(dto.getUpdatedTime());

        return community;
    }
}
