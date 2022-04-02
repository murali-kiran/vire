package com.vire.model.response;

import com.vire.dto.SocialPostLikeDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostLikeResponse implements Serializable {
    private  Long id;
    private  Long likerProfileId;
    private  Long socialPostId;
    private  Long likedTime;
    public static SocialPostLikeResponse fromDto(SocialPostLikeDto dto){
        var response = new SocialPostLikeResponse();
        response.setId(dto.getId());
        response.setLikerProfileId(dto.getLikerProfileId());
        response.setSocialPostId(dto.getSocialPostId());
        response.setLikedTime(dto.getLikedTime());
        return response;
    }
}
