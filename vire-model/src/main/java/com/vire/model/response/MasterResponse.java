package com.vire.model.response;

import com.vire.dto.MasterDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MasterResponse {

    private String masterId;
    
    private String masterType;
    private String masterValue;
    private Long createdTime;
    private Long updatedTime;

    public static MasterResponse fromDto(final MasterDto dto) {

        var master = new MasterResponse();

        master.setMasterId(String.valueOf(dto.getMasterId()));
        master.setMasterType(dto.getMasterType());
        master.setMasterValue(dto.getMasterValue());
        master.setCreatedTime(dto.getCreatedTime());
        master.setUpdatedTime(dto.getUpdatedTime());

        return master;
    }
}
