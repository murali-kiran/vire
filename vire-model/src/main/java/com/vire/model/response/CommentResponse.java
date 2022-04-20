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
    private Long createdTime;
    private Long updatedTime;
    public static CommentResponse fromDto(CommentDto dto){
        var response = new CommentResponse();
        response.setId(dto.getSocialPostCommentId());
        response.setCommenterProfileId(dto.getCommenterProfileId());
        response.setComment(dto.getComment());
        response.setSocialPostId(dto.getSocialId());
        response.setCommentTime(dto.getCommentTime());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
