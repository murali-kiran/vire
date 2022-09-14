package com.vire.model.response;

import com.vire.dto.SocialChatDto;
import com.vire.utils.Utility;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialChatResponse implements Serializable {
    private  String id;
    private  String chatInitiatorProfileId;
    private  String senderProfileId;
    private MinimalProfileResponse senderMinimalProfile;
    private  String message;
    private  String socialPostId;
    private Long createdTime;
    private Long updatedTime;
    private String messageTime;
    private String chatDate;
    public static SocialChatResponse fromDto(SocialChatDto dto){
        var response = new SocialChatResponse();
        response.setId(String.valueOf(dto.getId()));
        response.setChatInitiatorProfileId(String.valueOf(dto.getChatInitiatorProfileId()));
        response.setSenderProfileId(String.valueOf(dto.getSenderProfileId()));
        response.setMessage(dto.getMessage());
        response.setSocialPostId(String.valueOf(dto.getSocialPostId()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        response.setMessageTime(Utility.hhmmampmTimeFormat(dto.getCreatedTime()));
        response.setChatDate(Utility.DdMMMYYYYTimeFormat(dto.getCreatedTime()));
        if (dto.getSenderProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getSenderProfileId()));
            response.setSenderMinimalProfile(minProfileRes);
        }
        return response;
    }
}
