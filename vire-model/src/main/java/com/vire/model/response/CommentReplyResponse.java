package com.vire.model.response;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReplyResponse implements Serializable {
    private  Long id;
    private  Long commentReplierProfileId;
    private  String reply;
    private  Long commentId;
    private  Long replyTime;
    public static CommentReplyResponse fromDto(CommentReplyDto dto){
        var response = new CommentReplyResponse();
        response.setId(dto.getId());
        response.setCommentReplierProfileId(dto.getCommentReplierProfileId());
        response.setReply(dto.getReply());
        response.setCommentId(dto.getCommentId());
        response.setReplyTime(dto.getReplyTime());
        return response;
    }
}
