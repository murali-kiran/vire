package com.vire.model.request;

import com.vire.dto.SocialDto;
import lombok.Data;

@Data
public class SocialRequest {

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


    public SocialDto toDto() {
        var dto = new SocialDto();
        dto.setId(this.getId());
        dto.setUserId(this.getUserId());
        dto.setCategoryId(this.getCategoryId());
        dto.setType(this.getType());
        dto.setSubject(this.getSubject());
        dto.setDescription(this.getDescription());
        dto.setContact(this.getContact());
        dto.setAlternateContact(this.getAlternateContact());
        dto.setImagePath(this.getImagePath());
        dto.setCreatedTime(this.getCreatedTime());
        dto.setUpdatedTime(this.getUpdatedTime());
        return dto;
    }
}
