package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.CommunityProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityProfileRequest {

    private String communityProfileId;
    @NotBlank(message = "Community id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Community id must be numeric")
    private String communityId;
    @NotBlank(message = "Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Profile id must be numeric")
    private String profileId;

    public CommunityProfileDto toDto(Snowflake snowflake) {

        var dto = new CommunityProfileDto();

        if(snowflake == null) {
            dto.setCommunityProfileId(this.getCommunityProfileId() == null ? null : Long.valueOf(this.getCommunityProfileId()));
        }else {
            dto.setCommunityProfileId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setCommunityId(this.getCommunityId() == null ? null : Long.valueOf(this.getCommunityId()));

        return dto;
    }

    public CommunityProfileDto toDto() {
        return toDto(null);
    }
}