package com.vire.model.response;

import com.vire.dto.SocialPostChatDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostChatResponse implements Serializable {
    private  Long id;
    private  Long chatInitiatorProfileId;
    private  Long senderProfileId;
    private  String message;
    private  Long socialPostId;
    private  Long chatTime;

    public static SocialPostChatResponse fromDto(SocialPostChatDto dto){
        var response = new SocialPostChatResponse();
        response.setId(dto.getId());
        response.setChatInitiatorProfileId(dto.getChatInitiatorProfileId());
        response.setSenderProfileId(dto.getSenderProfileId());
        response.setMessage(dto.getMessage());
        response.setChatTime(dto.getChatTime());
        response.setSocialPostId(dto.getSocialPostId());
        return response;
    }
}
