package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.AppRestrictionDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AppRestrictionRequest {

    private String appRestrictionId;
    
    private Integer InterestsSelectionLimit;
    private Integer CreatingCommunitiesLimit;

    public AppRestrictionDto toDto(Snowflake snowflake) {

        var dto = new AppRestrictionDto();

        if(snowflake == null) {
            dto.setAppRestrictionId(this.getAppRestrictionId() == null ? null : Long.valueOf(this.getAppRestrictionId()));
        }else {
            dto.setAppRestrictionId(this.getAppRestrictionId() == null ? snowflake.nextId() : Long.valueOf(this.getAppRestrictionId()));
        }
        
        dto.setInterestsSelectionLimit(this.getInterestsSelectionLimit());
        dto.setCreatingCommunitiesLimit(this.getCreatingCommunitiesLimit());

        return dto;
    }

    public AppRestrictionDto toDto() {
        return toDto(null);
    }
}