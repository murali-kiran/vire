package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ProfileThumbsUpDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileThumbsUpRequest {

    private String profileThumbsUpId;
    
    private Long profileId;
    private Long thumbsUpBy;
    private String reason;
    private String description;

    public ProfileThumbsUpDto toDto(Snowflake snowflake) {

        var dto = new ProfileThumbsUpDto();

        if(snowflake == null) {
            dto.setProfileThumbsUpId(this.getProfileThumbsUpId() == null ? null : Long.valueOf(this.getProfileThumbsUpId()));
        }else {
            dto.setProfileThumbsUpId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId());
        dto.setThumbsUpBy(this.getThumbsUpBy());
        dto.setReason(this.getReason());
        dto.setDescription(this.getDescription());

        return dto;
    }

    public ProfileThumbsUpDto toDto() {
        return toDto(null);
    }
}