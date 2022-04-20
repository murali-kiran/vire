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
    private Long socialId;
    private Long createdTime;
    private Long updatedTime;
    public static CommentReplyResponse fromDto(CommentReplyDto dto){
        var response = new CommentReplyResponse();
        response.setId(dto.getSocialPostCommentReplyId());
        response.setCommentReplierProfileId(dto.getCommentReplierProfileId());
        response.setReply(dto.getReply());
        response.setCommentId(dto.getCommentId());
        response.setReplyTime(dto.getReplyTime());
        response.setSocialId(dto.getSocialId());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());

        return response;
    }
}
