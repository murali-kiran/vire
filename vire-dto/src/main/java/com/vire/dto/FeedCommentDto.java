package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class FeedCommentDto {
    private Long feedCommentId;
    private Long commentorProfileId;
    private Long feedId;
    private String comment;
    public Long createdTime;
    public Long updatedTime;
}
