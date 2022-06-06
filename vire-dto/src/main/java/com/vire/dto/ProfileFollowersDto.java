package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ProfileFollowersDto {
    private Long profileFollowersId;
    private Long profileId;
    private Long followerId;
    private Boolean isFriend;
    private String status;
    public Long createdTime;
    public Long updatedTime;
}
