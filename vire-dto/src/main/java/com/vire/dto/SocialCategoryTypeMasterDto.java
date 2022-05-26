package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class SocialCategoryTypeMasterDto {
    private Long socialCategoryTypeMasterId;
    private String categoryType;
    public Long createdTime;
    public Long updatedTime;
}
