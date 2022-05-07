package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.MasterDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MasterRequest {

    private String masterId;
    @NotBlank(message = "Master type required")
    private String masterType;
    @NotBlank(message = "Master value required")
    private String masterValue;

    public MasterDto toDto(Snowflake snowflake) {

        var dto = new MasterDto();

        if(snowflake == null) {
            dto.setMasterId(this.getMasterId() == null ? null : Long.valueOf(this.getMasterId()));
        }else {
            dto.setMasterId(snowflake.nextId());
        }
        
        dto.setMasterType(this.getMasterType());
        dto.setMasterValue(this.getMasterValue());

        return dto;
    }

    public MasterDto toDto() {
        return toDto(null);
    }
}