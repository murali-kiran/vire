package com.vire.model.request;

import com.vire.dto.SocialCategoryMasterDto;
import com.vire.utils.Snowflake;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialCategoryMasterRequest {

    private String socialCategoryMasterId;
    
    private String category;
    private String colorCode;
    private List<SocialCategoryTypeMasterRequest> categoryTypeList;
    private List<SocialCategorySentToMasterRequest> categorySendToList;

    public SocialCategoryMasterDto toDto(Snowflake snowflake) {

        var dto = new SocialCategoryMasterDto();

        if(snowflake == null) {
            dto.setSocialCategoryMasterId(this.getSocialCategoryMasterId() == null ? null : Long.valueOf(this.getSocialCategoryMasterId()));
        }else {
            dto.setSocialCategoryMasterId(snowflake.nextId());
        }
        
        dto.setCategory(this.getCategory());
        dto.setColorCode(this.getColorCode());

        if (this.getCategoryTypeList() != null && !this.getCategoryTypeList().isEmpty()) {
            dto.setCategoryTypeList(this.getCategoryTypeList()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }


        if (this.getCategorySendToList() != null && !this.getCategorySendToList().isEmpty()) {
            dto.setCategorySendToList(this.getCategorySendToList()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }


        return dto;
    }

    public SocialCategoryMasterDto toDto() {
        return toDto(null);
    }
}