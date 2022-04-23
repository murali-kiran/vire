package com.vire.model.response;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReplyResponse implements Serializable {
    private  String id;
    private  String commentReplierProfileId;
    private  String reply;
    private  String commentId;
    private String socialId;
    private Long createdTime;
    private Long updatedTime;
    public static CommentReplyResponse fromDto(CommentReplyDto dto){
        var response = new CommentReplyResponse();
        response.setId(dto.getSocialPostCommentReplyId().toString());
        response.setCommentReplierProfileId(dto.getCommentReplierProfileId().toString());
        response.setReply(dto.getReply());
        response.setCommentId(dto.getCommentId().toString().toString());
        response.setSocialId(dto.getSocialId().toString());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());

        return response;
    }
}
