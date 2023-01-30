package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ProfileBlockDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileBlockRequest {

    private String profileBlockId;
    
    private String profileId;
    private String blockedProfileId;

    public ProfileBlockDto toDto(Snowflake snowflake) {

        var dto = new ProfileBlockDto();

        if(snowflake == null) {
            dto.setProfileBlockId(this.getProfileBlockId() == null ? null : Long.valueOf(this.getProfileBlockId()));
        }else {
            dto.setProfileBlockId(this.getProfileBlockId() == null ? snowflake.nextId() : Long.valueOf(this.getProfileBlockId()));
        }
        
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setBlockedProfileId(this.getBlockedProfileId() == null ? null : Long.valueOf(this.getBlockedProfileId()));

        return dto;
    }

    public ProfileBlockDto toDto() {
        return toDto(null);
    }
}