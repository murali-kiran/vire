package com.vire.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class FeedCommentReplyDto {
    private Long feedCommentReplyId;
    private Long replierProfileId;
    private Long feedId;
    private Long commentId;
    private String reply;
    public Long createdTime;
    public Long updatedTime;
}
