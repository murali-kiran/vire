package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class SocialCategoryMasterDto {
    private Long socialCategoryMasterId;
    private String category;
    private String colorCode;
    private Boolean isPersonal;
    private List<SocialCategoryTypeMasterDto> categoryTypeList;
    private List<SocialCategorySentToMasterDto> categorySendToList;
    public Long createdTime;
    public Long updatedTime;
}
