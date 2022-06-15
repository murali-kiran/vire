package com.vire.model.response;

import com.vire.dto.FeedFileDto;
import lombok.Data;

@Data
public class FeedFileResponse {

    private String feedFileId;

    private String fileId;
    private Long createdTime;
    private Long updatedTime;

    public static FeedFileResponse fromDto(final FeedFileDto dto) {

        var feedFile = new FeedFileResponse();

        feedFile.setFeedFileId(String.valueOf(dto.getFeedFileId()));
        feedFile.setFileId(String.valueOf(dto.getFileId()));
        feedFile.setCreatedTime(dto.getCreatedTime());
        feedFile.setUpdatedTime(dto.getUpdatedTime());

        return feedFile;
    }
}
