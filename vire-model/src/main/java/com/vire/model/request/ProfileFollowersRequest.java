package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ProfileFollowersDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileFollowersRequest {

    private String profileFollowersId;
    
    private Long profileId;
    private Long followerId;
    private Boolean isFriend;

    public ProfileFollowersDto toDto(Snowflake snowflake) {

        var dto = new ProfileFollowersDto();

        if(snowflake == null) {
            dto.setProfileFollowersId(this.getProfileFollowersId() == null ? null : Long.valueOf(this.getProfileFollowersId()));
        }else {
            dto.setProfileFollowersId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId());
        dto.setFollowerId(this.getFollowerId());
        dto.setIsFriend(this.getIsFriend());

        return dto;
    }

    public ProfileFollowersDto toDto() {
        return toDto(null);
    }
}