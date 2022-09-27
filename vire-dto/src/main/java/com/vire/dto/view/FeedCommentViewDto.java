package com.vire.dto.view;

import com.vire.dto.FeedCommentReplyDto;
import lombok.Data;

import java.util.List;


@Data
public class FeedCommentViewDto {
    private Long feedCommentId;
    private Long commentorProfileId;
    private Long feedId;
    private String comment;
    public Long createdTime;
    public Long updatedTime;
}
