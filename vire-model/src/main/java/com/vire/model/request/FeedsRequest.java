package com.vire.model.request;

import com.vire.dto.FeedsDto;
import com.vire.utils.Snowflake;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedsRequest {

    private String feedId;
    @NotBlank(message = "Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Profile id must be numeric")
    private String profileId;
    //@NotBlank(message = "Description required")
    private String description;
    /*@NotBlank(message = "File id required")
    @Pattern(regexp="(^[0-9]*$)", message = "File id must be numeric")
    private String fileId;*/
    private List<FeedsSendToRequest> feedsSendTo;
    private List<FeedFileRequest> feedFileList;
    private String parentFeedId;
    private  Long createdTime;
    private  Long updatedTime;

    public FeedsDto toDto(Snowflake snowflake) {
        var dto = new FeedsDto();
        dto.setFeedId(this.getFeedId() == null ? null : Long.valueOf(this.getFeedId()));
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
       // dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        dto.setDescription(this.getDescription());
        dto.setParentFeedId(this.getParentFeedId() == null ? null : Long.valueOf(this.getParentFeedId()));
        if (this.getFeedsSendTo() != null && !this.getFeedsSendTo().isEmpty()) {
            dto.setFeedsSendTo(this.getFeedsSendTo()
                    .stream()
                    .map(feedsSendTo -> feedsSendTo.toDto())
                    .collect(Collectors.toList())
            );
        }

        if (this.getFeedFileList() != null && !this.getFeedFileList().isEmpty()) {
            dto.setFeedFileList(this.getFeedFileList()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }
        return  dto;
    }
}
