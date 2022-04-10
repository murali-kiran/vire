package com.vire.model.request;

import com.vire.dto.LikesDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class LikesRequest implements Serializable {
    private  Long id;
    private  Long likerProfileId;
    private  Long socialPostId;
    private  Long likedTime;
    public LikesDto toDto(){
        var dto = new LikesDto();
        dto.setId(this.getId());
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setLikedTime(this.getLikedTime());
        return dto;
    }
}
