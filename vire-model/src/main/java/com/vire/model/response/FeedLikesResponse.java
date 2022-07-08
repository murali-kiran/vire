package com.vire.model.response;

import com.vire.dto.FeedLikesDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedLikesResponse {

    private String feedLikesId;
    private MinimalProfileResponse likerProfile;
    private String likerProfileId;
    private String feedId;
    private Long createdTime;
    private Long updatedTime;

    public static FeedLikesResponse fromDto(final FeedLikesDto dto) {

        var feedLikes = new FeedLikesResponse();

        feedLikes.setFeedLikesId(String.valueOf(dto.getFeedLikesId()));
        if (dto.getLikerProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getLikerProfileId()));
            feedLikes.setLikerProfile(minProfileRes);
        }
        feedLikes.setLikerProfileId(String.valueOf(dto.getLikerProfileId()));
        feedLikes.setFeedId(String.valueOf(dto.getFeedId()));
        feedLikes.setCreatedTime(dto.getCreatedTime());
        feedLikes.setUpdatedTime(dto.getUpdatedTime());

        return feedLikes;
    }
}
