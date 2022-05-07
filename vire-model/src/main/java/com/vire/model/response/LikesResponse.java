package com.vire.model.response;

import com.vire.dto.LikesDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class LikesResponse implements Serializable {
    private  String id;
    private  String likerProfileId;
    private  String socialPostId;
    private Long createdTime;
    private Long updatedTime;
    public static LikesResponse fromDto(LikesDto dto){
        var response = new LikesResponse();
        response.setId(String.valueOf(dto.getId()));
        response.setLikerProfileId(String.valueOf(dto.getLikerProfileId()));
        response.setSocialPostId(String.valueOf(dto.getSocialId()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
