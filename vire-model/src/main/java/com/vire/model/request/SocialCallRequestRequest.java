package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.SocialCallRequestDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialCallRequestRequest {

    private String socialCallRequestId;
    
    private Long profileId;
    private Long requesterProfileId;
    private String status;

    public SocialCallRequestDto toDto(Snowflake snowflake) {

        var dto = new SocialCallRequestDto();

        if(snowflake == null) {
            dto.setSocialCallRequestId(this.getSocialCallRequestId() == null ? null : Long.valueOf(this.getSocialCallRequestId()));
        }else {
            dto.setSocialCallRequestId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId());
        dto.setRequesterProfileId(this.getRequesterProfileId());
        dto.setStatus(this.getStatus());

        return dto;
    }

    public SocialCallRequestDto toDto() {
        return toDto(null);
    }
}