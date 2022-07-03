package com.vire.model.response;

import com.vire.dto.ExperienceDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceDetailResponse {

    private String experienceId;
    private MinimalProfileResponse minimalProfileResponse;
    private String categoryId;
    private String profileId;
    //private String fileId;
    private String title;
    private String description;
    private String location;
    private List<ExperienceFileResponse> experienceFileList;
    private List<ExperienceCommentResponse> commentResponseList;
    private List<ExperienceLikesResponse> likesResponseList;
    private String createdTimeStr;
    private MasterResponse categoryResponse;
    private Integer commentsCount;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceDetailResponse fromDto(final ExperienceDto dto) {

        var experience = new ExperienceDetailResponse();

        experience.setExperienceId(String.valueOf(dto.getExperienceId()));
        experience.setCategoryId(String.valueOf(dto.getCategoryId()));
        experience.setProfileId(String.valueOf(dto.getProfileId()));
        //experience.setFileId(String.valueOf(dto.getFileId()));
        experience.setTitle(dto.getTitle());
        experience.setDescription(dto.getDescription());
        experience.setLocation(dto.getLocation());
        if (dto.getExperienceFileList() != null && !dto.getExperienceFileList().isEmpty()) {
            experience.setExperienceFileList(dto.getExperienceFileList()
                    .stream()
                    .map(child -> ExperienceFileResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }
        experience.setCreatedTime(dto.getCreatedTime());
        experience.setUpdatedTime(dto.getUpdatedTime());

        return experience;
    }
}
