package com.vire.model.request;

import com.vire.dto.CommunityProfileDto;
import com.vire.utils.Snowflake;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityProfileRequest {

    private String communityProfileId;
    @NotBlank(message = "Community id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Community id must be numeric")
    private String communityId;
    @NotBlank(message = "Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Profile id must be numeric")
    private String profileId;
    @NotBlank(message = "Status required")
    private String status;
    private Boolean isAdmin;
    private Boolean isCreator;
    //private String fileId;
    private List<CommunityProfileFileRequest> communityFileList;
    public CommunityProfileDto toDto(Snowflake snowflake) {

        var dto = new CommunityProfileDto();

        if(snowflake == null) {
            dto.setCommunityProfileId(this.getCommunityProfileId() == null ? null : Long.valueOf(this.getCommunityProfileId()));
        }else {
            dto.setCommunityProfileId(snowflake.nextId());
        }
        
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        dto.setCommunityId(this.getCommunityId() == null ? null : Long.valueOf(this.getCommunityId()));
        dto.setStatus(this.getStatus());
        //dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        if (this.getCommunityFileList() != null && !this.getCommunityFileList().isEmpty()) {
            dto.setCommunityFileList(this.getCommunityFileList()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }
        dto.setIsAdmin(this.getIsAdmin());
        return dto;
    }

    public CommunityProfileDto toDto() {
        return toDto(null);
    }
}