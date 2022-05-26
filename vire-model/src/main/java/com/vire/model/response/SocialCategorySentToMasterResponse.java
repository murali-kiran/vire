package com.vire.model.response;

import com.vire.dto.SocialCategorySentToMasterDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialCategorySentToMasterResponse {

    private String socialCategorySentToMasterId;
    
    private String categorySendTo;
    private Long createdTime;
    private Long updatedTime;

    public static SocialCategorySentToMasterResponse fromDto(final SocialCategorySentToMasterDto dto) {

        var socialCategorySentToMaster = new SocialCategorySentToMasterResponse();

        socialCategorySentToMaster.setSocialCategorySentToMasterId(String.valueOf(dto.getSocialCategorySentToMasterId()));
        socialCategorySentToMaster.setCategorySendTo(dto.getCategorySendTo());
        socialCategorySentToMaster.setCreatedTime(dto.getCreatedTime());
        socialCategorySentToMaster.setUpdatedTime(dto.getUpdatedTime());

        return socialCategorySentToMaster;
    }
}
