package com.vire.model.response;

import com.vire.dto.CommunityFileDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityFileResponse {

    private String communityFileId;
    
    private String fileId;
    private Long createdTime;
    private Long updatedTime;

    public static CommunityFileResponse fromDto(final CommunityFileDto dto) {

        var communityFile = new CommunityFileResponse();

        communityFile.setCommunityFileId(String.valueOf(dto.getCommunityFileId()));
        communityFile.setFileId(String.valueOf(dto.getFileId()));
        communityFile.setCreatedTime(dto.getCreatedTime());
        communityFile.setUpdatedTime(dto.getUpdatedTime());

        return communityFile;
    }
}
