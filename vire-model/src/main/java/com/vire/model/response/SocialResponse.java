package com.vire.model.response;


import com.vire.dto.SocialDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialResponse {

    private  Long socialId;
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
    private List<SocialSendToResponse> sendTo;

    public static SocialResponse fromDto(SocialDto dto){
        var response = new SocialResponse();
        response.setSocialId(dto.getSocialId());
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
        if (dto.getSendTo() != null && !dto.getSendTo().isEmpty()) {
            response.setSendTo(dto.getSendTo()
                    .stream()
                    .map(sendToDto -> SocialSendToResponse.fromDto(sendToDto))
                    .collect(Collectors.toList())
            );
        }
        return response;
    }
}
