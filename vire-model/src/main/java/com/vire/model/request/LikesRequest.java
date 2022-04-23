package com.vire.model.request;

import com.vire.dto.LikesDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class LikesRequest implements Serializable {
    private  String id;
    private  String likerProfileId;
    private  String socialPostId;
    private  Long likedTime;
    public LikesDto toDto(){
        var dto = new LikesDto();
        dto.setId(this.getId() == null ? null : Long.valueOf(this.getId()));
        dto.setLikerProfileId(this.getLikerProfileId() == null ? null : Long.valueOf(this.getLikerProfileId()));
        dto.setSocialId(this.getSocialPostId() == null ? null : Long.valueOf(this.getSocialPostId()));
        return dto;
    }
}
