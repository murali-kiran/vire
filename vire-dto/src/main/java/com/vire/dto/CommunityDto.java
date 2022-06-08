package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CommunityDto {
    private Long communityId;
    private String name;
    private String description;
    private Long creatorProfileId;
    private Long fileId;
    private String rules;
    private Boolean memberProofRequired;
    private List<CommunityProfileDto> communityProfiles;
    private List<CommunityFileDto> communityFileList;
    private String profileId;
    public Long createdTime;
    public Long updatedTime;
}
