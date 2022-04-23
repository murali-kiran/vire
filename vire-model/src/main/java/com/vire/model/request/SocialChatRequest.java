package com.vire.model.request;

import com.vire.dto.SocialChatDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatRequest implements Serializable {
    private  String id;
    private  String chatInitiatorProfileId;
    private  String senderProfileId;
    private  String message;
    private  String socialPostId;

    public SocialChatDto toDto(){
        var dto = new SocialChatDto();
        dto.setId(this.getId() == null ? null : Long.valueOf(this.getId()));
        dto.setChatInitiatorProfileId(this.getChatInitiatorProfileId() == null ? null : Long.valueOf(this.getChatInitiatorProfileId()));
        dto.setSenderProfileId(this.getSenderProfileId() == null ? null : Long.valueOf(this.getSenderProfileId()));
        dto.setMessage(this.getMessage());
        dto.setSocialPostId(this.getSocialPostId() == null ? null : Long.valueOf(this.getSocialPostId()));
        return dto;
    }
}
