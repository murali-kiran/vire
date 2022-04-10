package com.vire.model.request;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReplyRequest implements Serializable {
    private  Long id;
    private  Long commentReplierProfileId;
    private  String reply;
    private  Long commentId;
    private  Long replyTime;
    public CommentReplyDto toDto(){
        var dto = new CommentReplyDto();
        dto.setId(this.getId());
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId());
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId());
        dto.setReplyTime(this.getReplyTime());
        return dto;
    }
}
