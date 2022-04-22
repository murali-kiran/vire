package com.vire.model.response;

import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialSendToDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class FeedsSendToResponse implements Serializable {
    private  Long feedsSendToId;
    private String type;
    private  String value;
    private Long feedId;
    private Long createdTime;
    private Long updatedTime;
    public static FeedsSendToResponse fromDto(FeedsSendToDto dto){
        var response = new FeedsSendToResponse();
        response.setFeedsSendToId(dto.getFeedsSendToId());
        response.setType(dto.getType());
        response.setValue(dto.getValue());
        response.setFeedId(dto.getFeedId());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
