package com.vire.model.response;

import com.vire.dto.AppRestrictionDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AppRestrictionResponse {

    private String appRestrictionId;
    private Integer InterestsSelectionLimit;
    private Integer CreatingCommunitiesLimit;
    private Long createdTime;
    private Long updatedTime;

    public static AppRestrictionResponse fromDto(final AppRestrictionDto dto) {

        var appRestriction = new AppRestrictionResponse();

        appRestriction.setAppRestrictionId(String.valueOf(dto.getAppRestrictionId()));
        appRestriction.setInterestsSelectionLimit(dto.getInterestsSelectionLimit());
        appRestriction.setCreatingCommunitiesLimit(dto.getCreatingCommunitiesLimit());
        appRestriction.setCreatedTime(dto.getCreatedTime());
        appRestriction.setUpdatedTime(dto.getUpdatedTime());

        return appRestriction;
    }
}
