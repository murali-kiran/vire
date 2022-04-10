package com.vire.model.response;

import com.vire.dto.SocialPostSendToDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostSentResponse implements Serializable {
    private  Long sendToId;
    private  String type;
    private  String value;
    public static SocialPostSentResponse fromDto(SocialPostSendToDto dto){
        var response = new SocialPostSentResponse();
        response.setSendToId(dto.getSendToId());
        response.setType(dto.getType());
        response.setValue(dto.getValue());
        return response;
    }
}
