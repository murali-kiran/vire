package com.vire.model.response;

import com.vire.dto.CommentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentResponse implements Serializable {
    private  Long id;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialPostId;
    private  Long commentTime;
    public static CommentResponse fromDto(CommentDto dto){
        var response = new CommentResponse();
        response.setId(dto.getId());
        response.setCommenterProfileId(dto.getCommenterProfileId());
        response.setComment(dto.getComment());
        response.setSocialPostId(dto.getSocialPostId());
        response.setCommentTime(dto.getCommentTime());
        return response;
    }
}
