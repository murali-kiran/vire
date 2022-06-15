package com.vire.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedsDto {
    private Long feedId;
    private Long profileId;
    private String description;
    private Long fileId;
    private List<FeedsSendToDto> feedsSendTo;
    private List<FeedFileDto> feedFileList;
    private  Long createdTime;
    private  Long updatedTime;
}
