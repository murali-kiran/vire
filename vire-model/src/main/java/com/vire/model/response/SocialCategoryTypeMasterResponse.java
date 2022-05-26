package com.vire.model.response;

import com.vire.dto.SocialCategoryTypeMasterDto;
import lombok.Data;

@Data
public class SocialCategoryTypeMasterResponse {

    private String socialCategoryTypeMasterId;
    
    private String categoryType;
    private Long createdTime;
    private Long updatedTime;

    public static SocialCategoryTypeMasterResponse fromDto(final SocialCategoryTypeMasterDto dto) {

        var socialCategoryTypeMaster = new SocialCategoryTypeMasterResponse();

        socialCategoryTypeMaster.setSocialCategoryTypeMasterId(String.valueOf(dto.getSocialCategoryTypeMasterId()));
        socialCategoryTypeMaster.setCategoryType(dto.getCategoryType());
        socialCategoryTypeMaster.setCreatedTime(dto.getCreatedTime());
        socialCategoryTypeMaster.setUpdatedTime(dto.getUpdatedTime());

        return socialCategoryTypeMaster;
    }
}
