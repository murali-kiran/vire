package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatDto implements Serializable {
    private  Long id;
    private  Long socialCreatorProfileId;
    private  Long senderProfileId;
    private  String message;
    private  Long socialPostId;
    private  Boolean isCreatorMessage;
    private Long createdTime;
    private Long updatedTime;
}
