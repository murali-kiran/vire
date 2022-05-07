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
        response.setId(String.valueOf(dto.getSocialPostCommentId()));
        response.setCommenterProfileId(String.valueOf(dto.getCommenterProfileId()));
        response.setComment(dto.getComment());
        response.setSocialPostId(String.valueOf(dto.getSocialId()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
