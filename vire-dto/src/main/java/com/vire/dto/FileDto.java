package com.vire.dto;

import lombok.Data;

import java.io.File;

@Data
public class FileDto{
    private Long socialImageId;
    private String mimeType;
    private String imagePath;
    private Long imageSize;
    private File file;
}
