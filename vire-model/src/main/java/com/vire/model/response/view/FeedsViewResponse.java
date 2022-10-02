package com.vire.model.response.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.view.FeedCommentViewDto;
import com.vire.dto.view.FeedLikesViewDto;
import com.vire.dto.view.FeedReportViewDto;
import com.vire.dto.view.FeedsViewDto;
import com.vire.model.response.MinimalProfileResponse;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include. NON_NULL)
public class FeedsViewResponse {
    private Long feedId;
    private Long profileId;
    private String description;
    private Integer commentsCount;
    private Integer likesCount;
    private Integer reportsCount;
    private Long createdTime;
    private Long updatedTime;
    private List<FeedCommentViewDto> feedComments;
    private MinimalProfileResponse minimalProfileResponse;
    //private List<FeedLikesViewDto> feedLikes;
    //private List<FeedReportViewDto> feedReports;

    public static FeedsViewResponse fromDto(FeedsViewDto dto){

        var response = new FeedsViewResponse();
        response.setFeedId(dto.getFeedId());
        response.setDescription(dto.getDescription());
        response.setProfileId(dto.getProfileId());

        response.setCommentsCount(dto.getFeedComments().size());
        response.setLikesCount(dto.getFeedLikes().size());
        response.setReportsCount(dto.getFeedReports().size());
        response.setFeedComments(dto.getFeedComments());

        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());

        return response;
    }

}
