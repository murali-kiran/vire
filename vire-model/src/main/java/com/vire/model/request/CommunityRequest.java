package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityRequest {

    private String communityId;
    @NotBlank(message = "Community name required")
    private String name;
    @NotBlank(message = "Description required")
    private String description;
    @NotBlank(message = "Creator Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Creator Profile id must be numeric")
    private Long creatorProfileId;
    @NotBlank(message = "File id required")
    @Pattern(regexp="(^[0-9]*$)", message = "File id must be numeric")
    private Long fileId;
    private String rules;
    //private List<CommunityProfileRequest> profiles;

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

        /*if (this.getProfiles() != null && !this.getProfiles().isEmpty()) {
            dto.setProfiles(this.getProfiles()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }*/


        return dto;
    }

    public CommunityDto toDto() {
        return toDto(null);
    }
}