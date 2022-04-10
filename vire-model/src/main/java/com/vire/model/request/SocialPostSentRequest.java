package com.vire.model.request;

import com.vire.dto.SocialPostSendToDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostSentRequest implements Serializable {
    private  Long sendToId;
    private  String type;
    private  String value;
    public SocialPostSendToDto toDto(){
        var dto = new SocialPostSendToDto();
        dto.setSendToId(this.getSendToId());
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        return dto;
    }
}
