package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.MasterDetailsDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MasterDetailsRequest {

    private String masterDetailsId;
    
    private Long masterId;
    private String detailType;
    private String detailValue;

    public MasterDetailsDto toDto(Snowflake snowflake) {

        var dto = new MasterDetailsDto();

        if(snowflake == null) {
            dto.setMasterDetailsId(this.getMasterDetailsId() == null ? null : Long.valueOf(this.getMasterDetailsId()));
        }else {
            dto.setMasterDetailsId(snowflake.nextId());
        }
        
        dto.setMasterId(this.getMasterId());
        dto.setDetailType(this.getDetailType());
        dto.setDetailValue(this.getDetailValue());

        return dto;
    }

    public MasterDetailsDto toDto() {
        return toDto(null);
    }
}