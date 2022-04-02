package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostCommentReplyDto implements Serializable {
    private  Long id;
    private  Long commentReplierProfileId;
    private  String reply;
    private  Long commentId;
    private  Long replyTime;
}
