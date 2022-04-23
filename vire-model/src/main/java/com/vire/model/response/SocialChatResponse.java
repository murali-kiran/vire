package com.vire.model.response;

import com.vire.dto.SocialChatDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatResponse implements Serializable {
    private  String id;
    private  String chatInitiatorProfileId;
    private  String senderProfileId;
    private  String message;
    private  String socialPostId;
    private Long createdTime;
    private Long updatedTime;
    public static SocialChatResponse fromDto(SocialChatDto dto){
        var response = new SocialChatResponse();
        response.setId(dto.getId().toString());
        response.setChatInitiatorProfileId(dto.getChatInitiatorProfileId().toString());
        response.setSenderProfileId(dto.getSenderProfileId().toString());
        response.setMessage(dto.getMessage());
        response.setSocialPostId(dto.getSocialPostId().toString());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
