package com.vire.model.request;

import com.vire.dto.FeedFileDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class FeedFileRequest {

    private String feedFileId;
    
    private String fileId;

    public FeedFileDto toDto(Snowflake snowflake) {

        var dto = new FeedFileDto();

        if(snowflake == null) {
            dto.setFeedFileId(this.getFeedFileId() == null ? null : Long.valueOf(this.getFeedFileId()));
        }else {
            dto.setFeedFileId(snowflake.nextId());
        }
        
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));

        return dto;
    }

    public FeedFileDto toDto() {
        return toDto(null);
    }
}