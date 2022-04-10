package com.vire.model.response;

import com.vire.dto.FileDto;
import lombok.Data;

import java.io.File;

@Data
public class FileResponse {
    private Long socialImageId;
    private String mimeType;
    private String imagePath;
    private Long imageSize;

    public static FileResponse fromDto(final FileDto dto) {
        var response = new FileResponse();
        response.setSocialImageId(dto.getSocialImageId());
        response.setMimeType(dto.getMimeType());
        response.setImagePath(dto.getImagePath());
        response.setImageSize(dto.getImageSize());
        return response;
    }
}
