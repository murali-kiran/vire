package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ProfileThumbsDownDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileThumbsDownRequest {

    private String profileThumbsDownId;
    
    private Long profileId;
    private Long thumbsDownBy;
    private String reason;
    private String description;

    public ProfileThumbsDownDto toDto(Snowflake snowflake) {

        var dto = new ProfileThumbsDownDto();

        if(snowflake == null) {
            dto.setProfileThumbsDownId(this.getProfileThumbsDownId() == null ? null : Long.valueOf(this.getProfileThumbsDownId()));
        }else {
            dto.setProfileThumbsDownId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId());
        dto.setThumbsDownBy(this.getThumbsDownBy());
        dto.setReason(this.getReason());
        dto.setDescription(this.getDescription());

        return dto;
    }

    public ProfileThumbsDownDto toDto() {
        return toDto(null);
    }
}