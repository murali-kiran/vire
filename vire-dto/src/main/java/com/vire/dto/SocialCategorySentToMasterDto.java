package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class SocialCategorySentToMasterDto {
    private Long socialCategorySentToMasterId;
    private String categorySendTo;
    public Long createdTime;
    public Long updatedTime;
}
