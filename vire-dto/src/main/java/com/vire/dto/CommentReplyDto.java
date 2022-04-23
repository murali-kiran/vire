package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReplyDto implements Serializable {
    private  Long socialPostCommentReplyId;
    private  Long commentReplierProfileId;
    private  String reply;
    private  Long commentId;
    private  Long socialId;
    private Long createdTime;
    private Long updatedTime;
}
