package com.vire.model.request;

import com.vire.dto.FileDto;
import lombok.Data;

@Data
public class FileRequest {
    private String fileId;
    private String mimeType;
    private String fileCommonPath;
    private Long fileSize;
    public FileDto toDto() {
        var dto = new FileDto();
        dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        dto.setMimeType(this.getMimeType());
        dto.setFileCommonPath(this.getFileCommonPath());
        dto.setFileSize(this.getFileSize());
        return dto;
    }
}
