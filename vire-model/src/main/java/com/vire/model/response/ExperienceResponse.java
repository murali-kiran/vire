package com.vire.model.response;

import com.vire.dto.ExperienceDto;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceResponse {

    private String experienceId;
    
    private String categoryId;
    private String profileId;
    //private String fileId;
    private String title;
    private String description;
    private String location;
    private Long viewCount;
    private List<ExperienceFileResponse> experienceFileList;
    private Long createdTime;
    private Long updatedTime;

    public static ExperienceResponse fromDto(final ExperienceDto dto) {

        var experience = new ExperienceResponse();

        experience.setExperienceId(String.valueOf(dto.getExperienceId()));
        experience.setCategoryId(String.valueOf(dto.getCategoryId()));
        experience.setProfileId(String.valueOf(dto.getProfileId()));
        //experience.setFileId(String.valueOf(dto.getFileId()));
        experience.setTitle(dto.getTitle());
        experience.setDescription(dto.getDescription());
        experience.setViewCount(dto.getViewCount());
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
