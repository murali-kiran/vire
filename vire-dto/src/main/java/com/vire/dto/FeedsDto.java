package com.vire.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class FeedsDto {
    private Long feedId;
    private Long profileId;
    private String description;
    private Long fileId;
    private List<FeedsSendToDto> feedsSendTo;
    private List<FeedFileDto> feedFileList;
    private Long parentFeedId;
    private Boolean sendToFollowers;
    private  Long createdTime;
    private  Long updatedTime;
    private  Long deletedTime;
}
