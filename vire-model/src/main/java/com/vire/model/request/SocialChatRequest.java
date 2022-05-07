package com.vire.model.request;

import com.vire.dto.SocialChatDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class SocialChatRequest implements Serializable {
    private  String id;
    @NotBlank(message = "Chat Initiator profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Chat Initiator profile id must be numeric")
    private  String chatInitiatorProfileId;
    @NotBlank(message = "Sender profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Sender profile id must be numeric")
    private  String senderProfileId;
    private  String message;
    @NotBlank(message = "Social post id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Social post id must be numeric")
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
