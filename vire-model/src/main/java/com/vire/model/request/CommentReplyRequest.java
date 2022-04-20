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
    private Long socialId;
    public CommentReplyDto toDto(){
        var dto = new CommentReplyDto();
        dto.setSocialPostCommentReplyId(this.getId());
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId());
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId());
        dto.setSocialId(this.socialId);
        dto.setReplyTime(this.getReplyTime());
        return dto;
    }
}
