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
    private String creatorProfileId;
    private String fileId;
    private String rules;
    //private List<CommunityProfileResponse> profiles;
    private Long createdTime;
    private Long updatedTime;

    public static CommunityResponse fromDto(final CommunityDto dto) {

        var community = new CommunityResponse();

        community.setCommunityId(String.valueOf(dto.getCommunityId()));
        
        community.setName(dto.getName());
        community.setDescription(dto.getDescription());
        community.setCreatorProfileId(String.valueOf(dto.getCreatorProfileId()));
        community.setFileId(String.valueOf(dto.getFileId()));
        community.setRules(dto.getRules());

        /*if (dto.getProfiles() != null && !dto.getProfiles().isEmpty()) {
            community.setProfiles(dto.getProfiles()
                    .stream()
                    .map(child -> CommunityProfileResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }*/

        community.setCreatedTime(dto.getCreatedTime());
        community.setUpdatedTime(dto.getUpdatedTime());

        return community;
    }
}
