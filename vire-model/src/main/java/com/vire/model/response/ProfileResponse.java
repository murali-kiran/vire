package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;

@Data
public class ProfileResponse {
    
    private Long id;
    private Long userId;
    private String name;

    public static ProfileResponse fromDto(final ProfileDto dto) {
        var response = new ProfileResponse();

        response.setId(dto.getId());
        response.setUserId(dto.getUserId());
        response.setName(dto.getName());

        return response;
    }
}
