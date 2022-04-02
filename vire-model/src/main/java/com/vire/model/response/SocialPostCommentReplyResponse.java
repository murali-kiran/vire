package com.vire.model.response;

import com.vire.dto.SocialPostCommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostCommentReplyResponse implements Serializable {
    private  Long id;
    private  Long commentReplierProfileId;
    private  String reply;
    private  Long commentId;
    private  Long replyTime;
    public static SocialPostCommentReplyResponse fromDto(SocialPostCommentReplyDto dto){
        var response = new SocialPostCommentReplyResponse();
        response.setId(dto.getId());
        response.setCommentReplierProfileId(dto.getCommentReplierProfileId());
        response.setReply(dto.getReply());
        response.setCommentId(dto.getCommentId());
        response.setReplyTime(dto.getReplyTime());
        return response;
    }
}
