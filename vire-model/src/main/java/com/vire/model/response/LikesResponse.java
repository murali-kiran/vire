package com.vire.model.response;

import com.vire.dto.LikesDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class LikesResponse implements Serializable {
    private  Long id;
    private  Long likerProfileId;
    private  Long socialPostId;
    private  Long likedTime;
    private Long createdTime;
    private Long updatedTime;
    public static LikesResponse fromDto(LikesDto dto){
        var response = new LikesResponse();
        response.setId(dto.getId());
        response.setLikerProfileId(dto.getLikerProfileId());
        response.setSocialPostId(dto.getSocialId());
        response.setLikedTime(dto.getLikedTime());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
