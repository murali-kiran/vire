package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDto implements Serializable {
    private  Long socialPostCommentId;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialId;
    private Long createdTime;
    private Long updatedTime;
}
