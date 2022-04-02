package com.vire.model.response;

import com.vire.dto.SocialDto;
import lombok.Data;

@Data
public class SocialResponse {

    private  Long id;
    private  Long userId;
    private  Long categoryId;
    private  String type;
    private  String subject;
    private  String description;
    private  String contact;
    private  String alternateContact;
    private  String imagePath;
    private  Long createdTime;
    private  Long updatedTime;

    public static SocialResponse fromDto(final SocialDto dto) {
        var response = new SocialResponse();
        response.setId(dto.getId());
        response.setUserId(dto.getUserId());
        response.setCategoryId(dto.getCategoryId());
        response.setType(dto.getType());
        response.setSubject(dto.getSubject());
        response.setDescription(dto.getDescription());
        response.setContact(dto.getContact());
        response.setAlternateContact(dto.getAlternateContact());
        response.setImagePath(dto.getImagePath());
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }
}
