package com.vire.model.request;

import com.vire.dto.SocialImageDto;
import lombok.Data;

import java.io.File;

@Data
public class SocialImageRequest {
    private Long socialImageId;
    private String mimeType;
    private String imagePath;
    private Long imageSize;
    private File file;
    public SocialImageDto toDto() {
        var dto = new SocialImageDto();
        dto.setSocialImageId(this.getSocialImageId());
        dto.setMimeType(this.getMimeType());
        dto.setImagePath(this.getImagePath());
        dto.setImageSize(this.getImageSize());
        dto.setFile(this.getFile());
        return dto;
    }
}
