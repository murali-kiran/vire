package com.vire.model.response;

import com.vire.dto.SocialCategoryMasterDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialCategoryMasterResponse {

    private String socialCategoryMasterId;
    
    private String category;
    private String colorCode;
    private List<SocialCategoryTypeMasterResponse> categoryTypeList;
    private List<SocialCategorySentToMasterResponse> categorySendToList;
    private Long createdTime;
    private Long updatedTime;

    public static SocialCategoryMasterResponse fromDto(final SocialCategoryMasterDto dto) {

        var socialCategoryMaster = new SocialCategoryMasterResponse();

        socialCategoryMaster.setSocialCategoryMasterId(String.valueOf(dto.getSocialCategoryMasterId()));
        socialCategoryMaster.setCategory(dto.getCategory());
        socialCategoryMaster.setColorCode(dto.getColorCode());

        if (dto.getCategoryTypeList() != null && !dto.getCategoryTypeList().isEmpty()) {
            socialCategoryMaster.setCategoryTypeList(dto.getCategoryTypeList()
                    .stream()
                    .map(child -> SocialCategoryTypeMasterResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }


        if (dto.getCategorySendToList() != null && !dto.getCategorySendToList().isEmpty()) {
            socialCategoryMaster.setCategorySendToList(dto.getCategorySendToList()
                    .stream()
                    .map(child -> SocialCategorySentToMasterResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }

        socialCategoryMaster.setCreatedTime(dto.getCreatedTime());
        socialCategoryMaster.setUpdatedTime(dto.getUpdatedTime());

        return socialCategoryMaster;
    }
}
