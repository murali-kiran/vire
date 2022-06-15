package com.vire.model.response;


import com.vire.dto.FeedsDto;
import com.vire.dto.FeedsSendToDto;
import com.vire.dto.SocialDto;
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
    private  Long createdTime;
    private  Long updatedTime;

    public static FeedsResponse fromDto(FeedsDto dto){
        var response = new FeedsResponse();
        response.setFeedId(dto.getFeedId().toString());
        response.setProfileId(dto.getProfileId().toString());
        response.setDescription(dto.getDescription());
        //response.setFileId(dto.getFileId().toString());
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
