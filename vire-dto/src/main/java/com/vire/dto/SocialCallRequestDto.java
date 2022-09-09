package com.vire.dto;

import lombok.Data;



@Data
public class SocialCallRequestDto {
    private Long socialCallRequestId;
    private Long profileId;
    private Long requesterProfileId;
    private String status;
    private Long socialId;
    public Long createdTime;
    public Long updatedTime;
}
