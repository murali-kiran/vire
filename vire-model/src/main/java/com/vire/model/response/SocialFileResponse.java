package com.vire.model.response;

import com.vire.dto.SocialFileDto;
import lombok.Data;

@Data
public class SocialFileResponse {

    private String socialFileId;
    
    private String fileId;
    private Long createdTime;
    private Long updatedTime;

    public static SocialFileResponse fromDto(final SocialFileDto dto) {

        var socialFile = new SocialFileResponse();

        socialFile.setSocialFileId(String.valueOf(dto.getSocialFileId()));
        socialFile.setFileId(String.valueOf(dto.getFileId()));
        socialFile.setCreatedTime(dto.getCreatedTime());
        socialFile.setUpdatedTime(dto.getUpdatedTime());

        return socialFile;
    }
}
