package com.vire.model.request;

import com.vire.dto.SocialChatDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatRequest implements Serializable {
    private  Long id;
    private  Long chatInitiatorProfileId;
    private  Long senderProfileId;
    private  String message;
    private  Long socialPostId;
    private  Long chatTime;

    public SocialChatDto toDto(){
        var dto = new SocialChatDto();
        dto.setId(this.getId());
        dto.setChatInitiatorProfileId(this.getChatInitiatorProfileId());
        dto.setSenderProfileId(this.getSenderProfileId());
        dto.setMessage(this.getMessage());
        dto.setChatTime(this.getChatTime());
        dto.setSocialPostId(this.getSocialPostId());
        return dto;
    }
}
