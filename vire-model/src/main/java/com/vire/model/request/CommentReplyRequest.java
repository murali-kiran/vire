package com.vire.model.request;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReplyRequest implements Serializable {
    private  String id;
    private  String commentReplierProfileId;
    private  String reply;
    private  String commentId;
    private String socialId;
    public CommentReplyDto toDto(){
        var dto = new CommentReplyDto();
        dto.setSocialPostCommentReplyId(this.getId() == null ? null : Long.valueOf(this.getId()));
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId() == null ? null : Long.valueOf(this.getCommentReplierProfileId()));
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId() == null ? null : Long.valueOf(this.getCommentId()));
        dto.setSocialId(this.getSocialId() == null ? null : Long.valueOf(this.getSocialId()));
        return dto;
    }
}
