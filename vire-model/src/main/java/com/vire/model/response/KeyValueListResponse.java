package com.vire.model.response;

import com.vire.dto.AddressDto;
import com.vire.dto.CommunityDto;
import lombok.Data;

@Data
public class KeyValueListResponse {
    private String id;
    private String value;

    public static KeyValueListResponse fromDto(Long id, String value) {
        KeyValueListResponse response = new KeyValueListResponse();
        response.setId(String.valueOf(id));
        response.setValue(value);
        return response;
    }
}
