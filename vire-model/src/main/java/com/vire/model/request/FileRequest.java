package com.vire.model.request;

import com.vire.dto.FileDto;
import lombok.Data;

import java.io.File;

@Data
public class FileRequest {
    private Long socialImageId;
    private String mimeType;
    private String imagePath;
    private Long imageSize;
    public FileDto toDto() {
        var dto = new FileDto();
        dto.setSocialImageId(this.getSocialImageId());
        dto.setMimeType(this.getMimeType());
        dto.setImagePath(this.getImagePath());
        dto.setImageSize(this.getImageSize());
        return dto;
    }
}
