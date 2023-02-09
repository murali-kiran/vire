package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class AppRestrictionDto {
    private Long appRestrictionId;
    private Integer InterestsSelectionLimit;
    private Integer CreatingCommunitiesLimit;
    public Long createdTime;
    public Long updatedTime;
}
