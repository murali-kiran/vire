package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatDto implements Serializable {
    private  Long id;
    private  Long chatInitiatorProfileId;
    private  Long senderProfileId;
    private  String message;
    private  Long socialPostId;
    private Long createdTime;
    private Long updatedTime;
}
