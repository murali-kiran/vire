package com.vire.model.request;

import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialSendToDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class FeedsSendToRequest implements Serializable {
    private  String feedsSendToId;
    private String type;
    private  String value;
    public FeedsSendToDto toDto(){
        var dto = new FeedsSendToDto();
        dto.setFeedsSendToId(this.getFeedsSendToId() == null ? null : Long.valueOf(this.getFeedsSendToId()));
        dto.setType(this.getType());
        dto.setValue(this.getValue());
        return dto;
    }
}
