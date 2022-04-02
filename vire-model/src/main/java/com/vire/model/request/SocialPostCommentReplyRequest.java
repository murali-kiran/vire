package com.vire.model.request;

import com.vire.dto.SocialPostCommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostCommentReplyRequest implements Serializable {
    private  Long id;
    private  Long commentReplierProfileId;
    private  String reply;
    private  Long commentId;
    private  Long replyTime;
    public SocialPostCommentReplyDto toDto(){
        var dto = new SocialPostCommentReplyDto();
        dto.setId(this.getId());
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId());
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId());
        dto.setReplyTime(this.getReplyTime());
        return dto;
    }
}
