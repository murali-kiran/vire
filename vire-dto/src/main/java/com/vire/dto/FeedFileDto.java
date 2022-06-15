package com.vire.dto;

import lombok.Data;


@Data
public class FeedFileDto {
    private Long feedFileId;
    private Long fileId;
    public Long createdTime;
    public Long updatedTime;
}
