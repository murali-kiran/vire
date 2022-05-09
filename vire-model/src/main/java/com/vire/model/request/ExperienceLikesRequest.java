package com.vire.model.request;

import com.vire.utils.Snowflake;
import com.vire.dto.ExperienceLikesDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceLikesRequest {

    private String experienceLikesId;
    @NotBlank(message = "Replier Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Liker Profile id must be numeric")
    private String likerProfileId;
    @NotBlank(message = "Replier Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Experience id must be numeric")
    private String experienceId;

    public ExperienceLikesDto toDto(Snowflake snowflake) {

        var dto = new ExperienceLikesDto();

        if(snowflake == null) {
            dto.setExperienceLikesId(this.getExperienceLikesId() == null ? null : Long.valueOf(this.getExperienceLikesId()));
        }else {
            dto.setExperienceLikesId(snowflake.nextId());
        }
        
        dto.setLikerProfileId(this.getLikerProfileId() == null ? null : Long.valueOf(this.getLikerProfileId()));
        dto.setExperienceId(this.getExperienceId() == null ? null : Long.valueOf(this.getExperienceId()));

        return dto;
    }

    public ExperienceLikesDto toDto() {
        return toDto(null);
    }
}