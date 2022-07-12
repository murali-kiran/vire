package com.vire.model.response;

import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialSendToDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class FeedsSendToResponse implements Serializable {
    private String feedsSendToId;
    private String type;
    private String value;
    private String name;
    private String feedId;
    private Long createdTime;
    private Long updatedTime;
    public static FeedsSendToResponse fromDto(FeedsSendToDto dto){
        var response = new FeedsSendToResponse();
        response.setFeedsSendToId(dto.getFeedsSendToId().toString());
        response.setType(dto.getType());
        response.setValue(dto.getValue());
        response.setFeedId(dto.getFeedId().toString());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
