package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ProfileSettingDto {
    private Long profileSettingId;
    private String settingType;
    private Boolean isEnable;
    public Long createdTime;
    public Long updatedTime;
}
