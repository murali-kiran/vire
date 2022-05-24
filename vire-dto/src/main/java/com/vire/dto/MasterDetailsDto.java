package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class MasterDetailsDto {
    private Long masterDetailsId;
    private Long masterId;
    private String detailType;
    private String detailValue;
    public Long createdTime;
    public Long updatedTime;
}
