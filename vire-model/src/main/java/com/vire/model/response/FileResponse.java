package com.vire.model.response;

import com.vire.dto.FileDto;
import lombok.Data;

@Data
public class FileResponse {
    private Long socialImageId;
    private String mimeType;
    private String imagePath;
    private Long imageSize;
    private Long createdTime;
    private Long updatedTime;
    public static FileResponse fromDto(final FileDto dto) {
        var response = new FileResponse();
        response.setSocialImageId(dto.getSocialImageId());
        response.setMimeType(dto.getMimeType());
        response.setImagePath(dto.getImagePath());
        response.setImageSize(dto.getImageSize());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
