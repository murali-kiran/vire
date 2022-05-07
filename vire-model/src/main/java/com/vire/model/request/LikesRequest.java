package com.vire.model.request;

import com.vire.dto.LikesDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class LikesRequest implements Serializable {
    private  String id;
    @NotBlank(message = "Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Chat Initiator profile id must be numeric")
    private  String likerProfileId;
    @NotBlank(message = "Social post id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Social post id must be numeric")
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
