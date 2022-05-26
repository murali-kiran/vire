package com.vire.model.response;

import com.vire.dto.FileDto;
import lombok.Data;

@Data
public class FileResponse {
    private String fileId;
    private String mimeType;
    private String fileCommonPath;
    private String downloadPath;
    private Long fileSize;
    private Long createdTime;
    private Long updatedTime;
    public static FileResponse fromDto(final FileDto dto, final String relativeUrl) {
        var response = new FileResponse();
        response.setFileId(dto.getFileId().toString());
        response.setMimeType(dto.getMimeType());
        response.setFileCommonPath(relativeUrl + dto.getFileCommonPath());
        response.setDownloadPath(dto.getFileCommonPath());
        response.setFileSize(dto.getFileSize());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
