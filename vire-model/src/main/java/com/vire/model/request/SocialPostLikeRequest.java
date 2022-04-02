package com.vire.model.request;

import com.vire.dto.SocialPostLikeDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostLikeRequest implements Serializable {
    private  Long id;
    private  Long likerProfileId;
    private  Long socialPostId;
    private  Long likedTime;
    public SocialPostLikeDto toDto(){
        var dto = new SocialPostLikeDto();
        dto.setId(this.getId());
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setLikedTime(this.getLikedTime());
        return dto;
    }
}
