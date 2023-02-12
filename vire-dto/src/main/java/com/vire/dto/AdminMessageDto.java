package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class AdminMessageDto {
    private Long adminMessageId;
    private String messageType;
    private Long sendTo;
    private String message;
    public Long createdTime;
    public Long updatedTime;
}
