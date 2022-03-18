package com.vire.model.request;

import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
public class ProfileRequest {

    private Long id;
    private Long userId;
    private String name;

    public ProfileDto toDto() {
        var dto = new ProfileDto();

        dto.setId(this.getId());
        dto.setUserId(this.getUserId());
        dto.setName(this.getName());

        return dto;
    }
}
