package com.vire.model.response;


import com.vire.dto.FeedsDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedsResponse {

    private String feedId;
    private String profileId;
    private String description;
    //private String fileId;
    private List<FeedsSendToResponse> feedsSendTo;
    private List<FeedFileResponse> feedFileList;
    private String parentFeedId;
    private  Long createdTime;
    private  Long updatedTime;

    public static FeedsResponse fromDto(FeedsDto dto){
        var response = new FeedsResponse();
        response.setFeedId(dto.getFeedId() != null ? String.valueOf(dto.getFeedId()) : null);
        response.setProfileId(dto.getProfileId() != null ? String.valueOf(dto.getProfileId()) : null);
        response.setDescription(dto.getDescription());
        response.setParentFeedId(dto.getParentFeedId() != null ? String.valueOf(dto.getParentFeedId()) : null);
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        if (dto.getFeedsSendTo() != null && !dto.getFeedsSendTo().isEmpty()) {
            response.setFeedsSendTo(dto.getFeedsSendTo()
                    .stream()
                    .map(sendToDto -> FeedsSendToResponse.fromDto(sendToDto))
                    .collect(Collectors.toList()));
        }
        if (dto.getFeedFileList() != null && !dto.getFeedFileList().isEmpty()) {
            response.setFeedFileList(dto.getFeedFileList()
                    .stream()
                    .map(child -> FeedFileResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }
        return response;
    }
}
