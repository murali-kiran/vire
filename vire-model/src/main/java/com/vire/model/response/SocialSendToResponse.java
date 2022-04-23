package com.vire.model.response;

import com.vire.dto.SocialSendToDto;
import com.vire.enumeration.SendToEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialSendToResponse implements Serializable {
    private  String sendToId;
    private String type;
    private  String value;
    private String socialId;
    private Long createdTime;
    private Long updatedTime;
    public static SocialSendToResponse fromDto(SocialSendToDto dto){
        var response = new SocialSendToResponse();
        response.setSendToId(dto.getSendToId().toString());
        response.setType(dto.getType());
        response.setValue(dto.getValue());
        response.setSocialId(dto.getSocialId().toString());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
