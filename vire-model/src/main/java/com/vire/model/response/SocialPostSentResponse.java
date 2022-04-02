package com.vire.model.response;

import com.vire.dto.SocialPostSentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostSentResponse implements Serializable {
    private  Long id;
    private  String type;
    private  String value;
    public static SocialPostSentResponse fromDto(SocialPostSentDto dto){
        var response = new SocialPostSentResponse();
        response.setId(dto.getId());
        response.setType(dto.getType());
        response.setValue(dto.getValue());
        return response;
    }
}
