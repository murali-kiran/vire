package com.vire.model.response;

import com.vire.dto.SocialImageDto;
import lombok.Data;

import java.io.File;

@Data
public class SocialImageResponse {
    private Long socialImageId;
    private String mimeType;
    private String imagePath;
    private Long imageSize;
    private File file;

    public static SocialImageResponse fromDto(final SocialImageDto dto) {
        var response = new SocialImageResponse();
        response.setSocialImageId(dto.getSocialImageId());
        response.setMimeType(dto.getMimeType());
        response.setImagePath(dto.getImagePath());
        response.setImageSize(dto.getImageSize());
        response.setFile(dto.getFile());
        return response;
    }
}
