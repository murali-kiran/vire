package com.vire.model.request;

import com.vire.dto.SocialSendToDto;
import com.vire.enumeration.SendToEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialSendToRequest implements Serializable {
    private  String sendToId;
    private String type;
    private  String value;
    public SocialSendToDto toDto(){
        var dto = new SocialSendToDto();
        dto.setSendToId(this.getSendToId() == null ? null : Long.valueOf(this.getSendToId()));
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        return dto;
    }
}
