package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.FeedLikesDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedLikesRequest {

    private String feedLikesId;
    @NotBlank(message = "Liker Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Liker Profile id must be numeric")
    private String likerProfileId;
    @NotBlank(message = "Feed id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Feed id must be numeric")
    private String feedId;

    public FeedLikesDto toDto(Snowflake snowflake) {

        var dto = new FeedLikesDto();

        if(snowflake == null) {
            dto.setFeedLikesId(this.getFeedLikesId() == null ? null : Long.valueOf(this.getFeedLikesId()));
        }else {
            dto.setFeedLikesId(snowflake.nextId());
        }
        
        dto.setLikerProfileId(this.getLikerProfileId() == null ? null : Long.valueOf(this.getLikerProfileId()));
        dto.setFeedId(this.getFeedId() == null ? null : Long.valueOf(this.getFeedId()));

        return dto;
    }

    public FeedLikesDto toDto() {
        return toDto(null);
    }
}