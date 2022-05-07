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
        response.setId(String.valueOf(dto.getSocialPostCommentReplyId()));
        response.setCommentReplierProfileId(String.valueOf(dto.getCommentReplierProfileId()));
        response.setReply(dto.getReply());
        response.setCommentId(String.valueOf(dto.getCommentId()));
        response.setSocialId(dto.getSocialId().toString());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());

        return response;
    }
}
