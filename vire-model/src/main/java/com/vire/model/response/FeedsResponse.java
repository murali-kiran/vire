package com.vire.model.response;


import com.vire.dto.FeedsDto;
import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedsResponse {

    private Long feedId;
    private Long profileId;
    private String description;
    private Long imageId;
    private List<FeedsSendToResponse> feedsSendTo;
    private  Long createdTime;
    private  Long updatedTime;

    public static FeedsResponse fromDto(FeedsDto dto){
        var response = new FeedsResponse();
        response.setFeedId(dto.getFeedId());
        response.setProfileId(dto.getProfileId());
        response.setDescription(dto.getDescription());
        response.setImageId(dto.getImageId());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        if (dto.getFeedsSendTo() != null && !dto.getFeedsSendTo().isEmpty()) {
            response.setFeedsSendTo(dto.getFeedsSendTo()
                    .stream()
                    .map(sendToDto -> FeedsSendToResponse.fromDto(sendToDto))
                    .collect(Collectors.toList()));
        }
        return response;
    }
}
