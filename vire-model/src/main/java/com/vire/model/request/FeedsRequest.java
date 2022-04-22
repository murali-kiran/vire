package com.vire.model.request;

import com.vire.dto.FeedsDto;
import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class FeedsRequest {

    private Long feedId;
    private Long profileId;
    private String description;
    private Long imageId;
    private List<FeedsSendToDto> feedsSendTo;
    private  Long createdTime;
    private  Long updatedTime;

    public FeedsDto toDto() {
        return  new ModelMapper().map(this,FeedsDto.class);
    }
}
