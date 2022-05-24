package com.vire.model.response;

import com.vire.dto.ProfileFollowersDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileFollowersResponse {

    private String profileFollowersId;
    
    private Long profileId;
    private Long followerId;
    private Boolean isFriend;
    private Long createdTime;
    private Long updatedTime;

    public static ProfileFollowersResponse fromDto(final ProfileFollowersDto dto) {

        var profileFollowers = new ProfileFollowersResponse();

        profileFollowers.setProfileFollowersId(String.valueOf(dto.getProfileFollowersId()));
        profileFollowers.setProfileId(dto.getProfileId());
        profileFollowers.setFollowerId(dto.getFollowerId());
        profileFollowers.setIsFriend(dto.getIsFriend());
        profileFollowers.setCreatedTime(dto.getCreatedTime());
        profileFollowers.setUpdatedTime(dto.getUpdatedTime());

        return profileFollowers;
    }
}
