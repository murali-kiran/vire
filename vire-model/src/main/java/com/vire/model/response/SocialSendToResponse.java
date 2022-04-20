package com.vire.model.response;

import com.vire.dto.SocialSendToDto;
import com.vire.enumeration.SendToEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialSendToResponse implements Serializable {
    private  Long sendToId;
    private String type;
    private  String value;
    private Long socialId;
    private Long createdTime;
    private Long updatedTime;
    public static SocialSendToResponse fromDto(SocialSendToDto dto){
        var response = new SocialSendToResponse();
        response.setSendToId(dto.getSendToId());
        response.setType(dto.getType());
        response.setValue(dto.getValue());
        response.setSocialId(dto.getSocialId());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
