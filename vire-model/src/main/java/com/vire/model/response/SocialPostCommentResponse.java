package com.vire.model.response;

import com.vire.dto.SocialPostCommentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostCommentResponse implements Serializable {
    private  Long id;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialPostId;
    private  Long commentTime;
    public static SocialPostCommentResponse fromDto(SocialPostCommentDto dto){
        var response = new SocialPostCommentResponse();
        response.setId(dto.getId());
        response.setCommenterProfileId(dto.getCommenterProfileId());
        response.setComment(dto.getComment());
        response.setSocialPostId(dto.getSocialPostId());
        response.setCommentTime(dto.getCommentTime());
        return response;
    }
}
