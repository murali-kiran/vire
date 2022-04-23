package com.vire.model.response;

import com.vire.dto.CommentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentResponse implements Serializable {
    private  String id;
    private  String commenterProfileId;
    private  String comment;
    private  String socialPostId;
    private Long createdTime;
    private Long updatedTime;
    public static CommentResponse fromDto(CommentDto dto){
        var response = new CommentResponse();
        response.setId(dto.getSocialPostCommentId().toString());
        response.setCommenterProfileId(dto.getCommenterProfileId().toString());
        response.setComment(dto.getComment());
        response.setSocialPostId(dto.getSocialId().toString());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
