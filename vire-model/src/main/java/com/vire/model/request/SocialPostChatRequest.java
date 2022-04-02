package com.vire.model.request;

import com.vire.dto.SocialPostChatDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostChatRequest implements Serializable {
    private  Long id;
    private  Long chatInitiatorProfileId;
    private  Long senderProfileId;
    private  String message;
    private  Long socialPostId;
    private  Long chatTime;

    public SocialPostChatDto toDto(){
        var dto = new SocialPostChatDto();
        dto.setId(this.getId());
        dto.setChatInitiatorProfileId(this.getChatInitiatorProfileId());
        dto.setSenderProfileId(this.getSenderProfileId());
        dto.setMessage(this.getMessage());
        dto.setChatTime(this.getChatTime());
        dto.setSocialPostId(this.getSocialPostId());
        return dto;
    }
}
