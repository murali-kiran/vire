package com.vire.dto.view;

import lombok.Data;


@Data
public class FeedLikesViewDto {
    private Long feedLikesId;
    private Long likerProfileId;
    private Long feedId;
    public Long createdTime;
    public Long updatedTime;
}
