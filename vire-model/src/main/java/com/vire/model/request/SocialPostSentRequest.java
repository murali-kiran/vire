package com.vire.model.request;

import com.vire.dto.SocialPostSentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostSentRequest implements Serializable {
    private  Long id;
    private  String type;
    private  String value;
    public SocialPostSentDto toDto(){
        var dto = new SocialPostSentDto();
        dto.setId(this.getId());
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        return dto;
    }
}
