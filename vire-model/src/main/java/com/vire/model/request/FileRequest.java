package com.vire.model.request;

import com.vire.dto.FileDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class FileRequest {
    private String fileId;
    private String mimeType;
    private String fileCommonPath;
    private Long fileSize;
    public FileDto toDto(Snowflake snowflake) {
        var dto = new FileDto();
        if(snowflake == null) {
            dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        }else {
            dto.setFileId(snowflake.nextId());
        }
        dto.setMimeType(this.getMimeType());
        dto.setFileCommonPath(this.getFileCommonPath());
        dto.setFileSize(this.getFileSize());
        return dto;
    }
}
