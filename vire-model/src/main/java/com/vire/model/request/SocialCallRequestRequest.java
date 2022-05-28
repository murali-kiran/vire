package com.vire.model.request;

import com.vire.dto.SocialCallRequestDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class SocialCallRequestRequest {

    private String socialCallRequestId;
    
    private String profileId;
    private String requesterProfileId;
    private String status;
    private String socialId;

    public SocialCallRequestDto toDto(Snowflake snowflake) {

        var dto = new SocialCallRequestDto();

        if(snowflake == null) {
            dto.setSocialCallRequestId(this.getSocialCallRequestId() == null ? null : Long.valueOf(this.getSocialCallRequestId()));
        }else {
            dto.setSocialCallRequestId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setSocialId(this.getSocialId() == null ? null : Long.valueOf(this.getSocialId()));
        dto.setRequesterProfileId(this.getRequesterProfileId() == null ? null : Long.valueOf(this.getRequesterProfileId()));
        dto.setStatus(this.getStatus());

        return dto;
    }

    public SocialCallRequestDto toDto() {
        return toDto(null);
    }
}