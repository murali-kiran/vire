package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostCommentDto implements Serializable {
    private  Long id;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialPostId;
    private  Long commentTime;
}
