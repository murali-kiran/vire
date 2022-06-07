package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CommunityFileDto {
    private Long communityFileId;
    private Long fileId;
    public Long createdTime;
    public Long updatedTime;
}
