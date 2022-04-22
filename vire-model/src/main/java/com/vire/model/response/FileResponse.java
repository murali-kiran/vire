package com.vire.model.response;

import com.vire.dto.FileDto;
import lombok.Data;

@Data
public class FileResponse {
    private Long fileId;
    private String mimeType;
    private String fileCommonPath;
    private Long fileSize;
    private Long createdTime;
    private Long updatedTime;
    public static FileResponse fromDto(final FileDto dto) {
        var response = new FileResponse();
        response.setFileId(dto.getFileId());
        response.setMimeType(dto.getMimeType());
        response.setFileCommonPath(dto.getFileCommonPath());
        response.setFileSize(dto.getFileSize());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
