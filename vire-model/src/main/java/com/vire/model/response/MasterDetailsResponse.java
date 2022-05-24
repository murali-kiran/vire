package com.vire.model.response;

import com.vire.dto.MasterDetailsDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MasterDetailsResponse {

    private String masterDetailsId;
    
    private Long masterId;
    private String detailType;
    private String detailValue;
    private Long createdTime;
    private Long updatedTime;

    public static MasterDetailsResponse fromDto(final MasterDetailsDto dto) {

        var masterDetails = new MasterDetailsResponse();

        masterDetails.setMasterDetailsId(String.valueOf(dto.getMasterDetailsId()));
        masterDetails.setMasterId(dto.getMasterId());
        masterDetails.setDetailType(dto.getDetailType());
        masterDetails.setDetailValue(dto.getDetailValue());
        masterDetails.setCreatedTime(dto.getCreatedTime());
        masterDetails.setUpdatedTime(dto.getUpdatedTime());

        return masterDetails;
    }
}
