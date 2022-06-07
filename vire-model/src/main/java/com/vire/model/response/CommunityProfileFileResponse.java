package com.vire.model.response;

import com.vire.dto.CommunityProfileFileDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityProfileFileResponse {

    private String communityProfileFileId;
    
    private String fileId;
    private Long createdTime;
    private Long updatedTime;

    public static CommunityProfileFileResponse fromDto(final CommunityProfileFileDto dto) {

        var communityProfileFile = new CommunityProfileFileResponse();

        communityProfileFile.setCommunityProfileFileId(String.valueOf(dto.getCommunityProfileFileId()));
        communityProfileFile.setFileId(String.valueOf(dto.getFileId()));
        communityProfileFile.setCreatedTime(dto.getCreatedTime());
        communityProfileFile.setUpdatedTime(dto.getUpdatedTime());

        return communityProfileFile;
    }
}
