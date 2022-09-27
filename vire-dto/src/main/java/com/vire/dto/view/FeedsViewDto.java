package com.vire.dto.view;

import lombok.Data;

import java.util.List;

@Data
public class FeedsViewDto {
    private Long feedId;
    private Long profileId;
    private String description;
    private List<FeedCommentViewDto> feedComments;
    private List<FeedLikesViewDto> feedLikes;
    private List<FeedReportViewDto> feedReports;
    private  Long createdTime;
    private  Long updatedTime;
}
