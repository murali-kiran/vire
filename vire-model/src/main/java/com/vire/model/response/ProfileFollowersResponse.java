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
    //private Long createdTime;
    //private Long updatedTime;

    public static ProfileFollowersResponse fromDto(final ProfileFollowersDto dto) {

        var profileFollowers = new ProfileFollowersResponse();

        profileFollowers.setProfileFollowersId(String.valueOf(dto.getProfileFollowersId()));
        profileFollowers.setProfileId(dto.getProfileId() == null ? null : String.valueOf(dto.getProfileId()));
        profileFollowers.setFollowerId(dto.getFollowerId() == null ? null : String.valueOf(dto.getFollowerId()));
        //profileFollowers.setIsFriend(dto.getIsFriend());
        profileFollowers.setStatus(dto.getStatus());
        //profileFollowers.setCreatedTime(dto.getCreatedTime());
        //profileFollowers.setUpdatedTime(dto.getUpdatedTime());

        return profileFollowers;
    }
}
