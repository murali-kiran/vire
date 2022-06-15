package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ExperienceDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceRequest {

    private String experienceId;
    @NotBlank(message = "Category id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Category id must be numeric")
    private String categoryId;
    @NotBlank(message = "Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Profile id must be numeric")
    private String profileId;
    @NotBlank(message = "File id required")
    /*@Pattern(regexp="(^[0-9]*$)", message = "File id must be numeric")
    private String fileId;*/
    @NotBlank(message = "Title required")
    private String title;
    @NotBlank(message = "Description required")
    private String description;
    @NotBlank(message = "Location required")
    private String location;
    private List<ExperienceFileRequest> experienceFileList;

    public ExperienceDto toDto(Snowflake snowflake) {

        var dto = new ExperienceDto();

        if(snowflake == null) {
            dto.setExperienceId(this.getExperienceId() == null ? null : Long.valueOf(this.getExperienceId()));
        }else {
            dto.setExperienceId(snowflake.nextId());
        }
        
        dto.setCategoryId(this.getCategoryId() == null ? null : Long.valueOf(this.getCategoryId()));
        dto.setProfileId(this.getProfileId() == null ? null : Long.valueOf(this.getProfileId()));
        //dto.setFileId(this.getFileId() == null ? null : Long.valueOf(this.getFileId()));
        dto.setTitle(this.getTitle());
        dto.setDescription(this.getDescription());
        dto.setLocation(this.getLocation());
        if (this.getExperienceFileList() != null && !this.getExperienceFileList().isEmpty()) {
            dto.setExperienceFileList(this.getExperienceFileList()
                    .stream()
                    .map(child -> child.toDto(snowflake))
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public ExperienceDto toDto() {
        return toDto(null);
    }
}