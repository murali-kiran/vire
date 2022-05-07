package com.vire.model.response;

import com.vire.dto.FeedLikesDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedLikesResponse {

    private String feedLikesId;
    
    private String likerProfileId;
    private String feedId;
    private Long createdTime;
    private Long updatedTime;

    public static FeedLikesResponse fromDto(final FeedLikesDto dto) {

        var feedLikes = new FeedLikesResponse();

        feedLikes.setFeedLikesId(String.valueOf(dto.getFeedLikesId()));
        
        feedLikes.setLikerProfileId(String.valueOf(dto.getLikerProfileId()));
        feedLikes.setFeedId(String.valueOf(dto.getFeedId()));
        feedLikes.setCreatedTime(dto.getCreatedTime());
        feedLikes.setUpdatedTime(dto.getUpdatedTime());

        return feedLikes;
    }
}
