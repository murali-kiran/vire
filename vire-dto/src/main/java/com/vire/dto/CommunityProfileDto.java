package com.vire.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CommunityProfileDto {
    private Long communityProfileId;
    private Long profileId;
    private Long communityId;
    private String status;
    private Boolean isAdmin;
    private List<CommunityProfileFileDto> communityFileList;
    //private Long fileId;
    public Long createdTime;
    public Long updatedTime;
}
