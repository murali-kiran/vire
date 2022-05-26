package com.vire.model.request;

import com.vire.dto.SocialCategorySentToMasterDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class SocialCategorySentToMasterRequest {

    private String socialCategorySentToMasterId;
    
    private String categorySendTo;

    public SocialCategorySentToMasterDto toDto(Snowflake snowflake) {

        var dto = new SocialCategorySentToMasterDto();

        if(snowflake == null) {
            dto.setSocialCategorySentToMasterId(this.getSocialCategorySentToMasterId() == null ? null : Long.valueOf(this.getSocialCategorySentToMasterId()));
        }else {
            dto.setSocialCategorySentToMasterId(snowflake.nextId());
        }
        
        dto.setCategorySendTo(this.getCategorySendTo());

        return dto;
    }

    public SocialCategorySentToMasterDto toDto() {
        return toDto(null);
    }
}