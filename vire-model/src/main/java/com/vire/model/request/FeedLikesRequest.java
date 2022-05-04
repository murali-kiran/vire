package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedLikesDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedLikesRequest {

    private String feedLikesId;
    
    private Long likerProfileId;
    private Long feedId;

    public FeedLikesDto toDto(Snowflake snowflake) {

        var dto = new FeedLikesDto();

        if(snowflake == null) {
            dto.setFeedLikesId(this.getFeedLikesId() == null ? null : Long.valueOf(this.getFeedLikesId()));
        }else {
            dto.setFeedLikesId(snowflake.nextId());
        }
        
        dto.setLikerProfileId(this.getLikerProfileId());
        dto.setFeedId(this.getFeedId());

        return dto;
    }

    public FeedLikesDto toDto() {
        return toDto(null);
    }
}