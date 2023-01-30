package com.vire.model.response;

import com.vire.dto.ProfileBlockDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileBlockResponse {

    private String profileBlockId;
    
    private String profileId;
    private String blockedProfileId;
    private MinimalProfileResponse BlockerProfile;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileBlockResponse fromDto(final ProfileBlockDto dto) {

        var profileBlock = new ProfileBlockResponse();

        profileBlock.setProfileBlockId(String.valueOf(dto.getProfileBlockId()));
        profileBlock.setProfileId(String.valueOf(dto.getProfileId()));
        profileBlock.setBlockedProfileId(String.valueOf(dto.getBlockedProfileId()));

        if (dto.getBlockedProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getBlockedProfileId()));
            profileBlock.setBlockerProfile(minProfileRes);
        }
        profileBlock.setCreatedTime(dto.getCreatedTime());
        profileBlock.setUpdatedTime(dto.getUpdatedTime());

        return profileBlock;
    }
}
