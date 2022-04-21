package com.vire.model.response;

import com.vire.dto.SocialChatDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatResponse implements Serializable {
    private  Long id;
    private  Long chatInitiatorProfileId;
    private  Long senderProfileId;
    private  String message;
    private  Long socialPostId;
    private  Long chatTime;
    private Long createdTime;
    private Long updatedTime;
    public static SocialChatResponse fromDto(SocialChatDto dto){
        var response = new SocialChatResponse();
        response.setId(dto.getId());
        response.setChatInitiatorProfileId(dto.getChatInitiatorProfileId());
        response.setSenderProfileId(dto.getSenderProfileId());
        response.setMessage(dto.getMessage());
        response.setChatTime(dto.getChatTime());
        response.setSocialPostId(dto.getSocialPostId());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
