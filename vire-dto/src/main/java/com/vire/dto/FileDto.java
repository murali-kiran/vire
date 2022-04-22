package com.vire.dto;

import lombok.Data;

import java.io.File;

@Data
public class FileDto{
    private Long fileId;
    private String mimeType;
    private String fileCommonPath;
    private Long fileSize;
    private File file;
    private Long createdTime;
    private Long updatedTime;
}
