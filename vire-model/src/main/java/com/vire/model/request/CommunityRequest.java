package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityRequest {

    private String communityId;
    
    private String name;
    private String description;
    private Long creatorProfileId;
    private Long fileId;
    private String rules;
    private List<CommunityProfileRequest> profiles;

    public CommunityDto toDto(Snowflake snowflake) {

        var dto = new CommunityDto();

        if(snowflake == null) {
            dto.setCommunityId(this.getCommunityId() == null ? null : Long.valueOf(this.getCommunityId()));
        }else {
            dto.setCommunityId(snowflake.nextId());
        }
        
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setCreatorProfileId(this.getCreatorProfileId());
        dto.setFileId(this.getFileId());
        dto.setRules(this.getRules());

        if (this.getProfiles() != null && !this.getProfiles().isEmpty()) {
            dto.setProfiles(this.getProfiles()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }


        return dto;
    }

    public CommunityDto toDto() {
        return toDto(null);
    }
}