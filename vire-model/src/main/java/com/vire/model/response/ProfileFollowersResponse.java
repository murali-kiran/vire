package com.vire.model.response;

import com.vire.dto.ProfileFollowersDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileFollowersResponse {

    private String profileFollowersId;
    
    private String profileId;
    private String followerId;
    //private Boolean isFriend;
    private String status;
    private MinimalProfileResponse follower;
    private MinimalProfileResponse profile;

    public static ProfileFollowersResponse fromDto(final ProfileFollowersDto dto) {

        var profileFollowers = new ProfileFollowersResponse();

        profileFollowers.setProfileFollowersId(String.valueOf(dto.getProfileFollowersId()));
        profileFollowers.setProfileId(dto.getProfileId() == null ? null : String.valueOf(dto.getProfileId()));
        profileFollowers.setFollowerId(dto.getFollowerId() == null ? null : String.valueOf(dto.getFollowerId()));
        //profileFollowers.setIsFriend(dto.getIsFriend());
        profileFollowers.setStatus(dto.getStatus());
        //profileFollowers.setCreatedTime(dto.getCreatedTime());
        //profileFollowers.setUpdatedTime(dto.getUpdatedTime());
        if (dto.getFollowerId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getFollowerId()));
            profileFollowers.setFollower(minProfileRes);
        }
        if (dto.getProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getProfileId()));
            profileFollowers.setProfile(minProfileRes);
        }
        return profileFollowers;
    }
}
