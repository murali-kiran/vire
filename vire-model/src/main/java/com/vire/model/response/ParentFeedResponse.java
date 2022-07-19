package com.vire.model.response;

import lombok.Data;
import java.util.List;

@Data
public class ParentFeedResponse {

    private String feedId;
    private String description;
    private MinimalProfileResponse minimalProfileResponse;
    private List<FeedsSendToResponse> feedsSendTo;
    private List<FeedFileResponse> feedFileList;
    private String createdTimeStr;
    private String location;
}
