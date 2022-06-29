package com.vire.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommentDto implements Serializable {
    private  Long socialPostCommentId;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialId;
    private List<CommentReplyDto> commentReplyDtoList;
    private Long createdTime;
    private Long updatedTime;
}
