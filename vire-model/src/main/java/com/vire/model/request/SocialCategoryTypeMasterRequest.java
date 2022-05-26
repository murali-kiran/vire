package com.vire.model.request;

import com.vire.dto.SocialCategoryTypeMasterDto;
import com.vire.utils.Snowflake;
import lombok.Data;

@Data
public class SocialCategoryTypeMasterRequest {

    private String socialCategoryTypeMasterId;
    
    private String categoryType;

    public SocialCategoryTypeMasterDto toDto(Snowflake snowflake) {

        var dto = new SocialCategoryTypeMasterDto();

        if(snowflake == null) {
            dto.setSocialCategoryTypeMasterId(this.getSocialCategoryTypeMasterId() == null ? null : Long.valueOf(this.getSocialCategoryTypeMasterId()));
        }else {
            dto.setSocialCategoryTypeMasterId(snowflake.nextId());
        }
        
        dto.setCategoryType(this.getCategoryType());

        return dto;
    }

    public SocialCategoryTypeMasterDto toDto() {
        return toDto(null);
    }
}