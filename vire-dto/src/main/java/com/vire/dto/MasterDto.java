package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class MasterDto {
    private Long masterId;
    private String masterType;
    private String masterValue;
    public Long createdTime;
    public Long updatedTime;
    private  List<MasterDetailsDto> sendTo;
}
