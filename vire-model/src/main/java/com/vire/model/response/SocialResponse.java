package com.vire.model.response;


import com.vire.dto.SocialDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialResponse {

    private  String socialId;
    private  String profileId;
    private  MinimalProfileResponse minimalProfileResponse;
    private  String categoryId;
    private  String type;
    private  String subject;
    private  String description;
    private  String fileId;
    private  String contact;
    private  String alternateContact;
    private int shareContact;
    private int shareAlternate;
    private  Long createdTime;
    private  Long updatedTime;
    private List<SocialSendToResponse> sendTo;
    private List<SocialCallRequestResponse> socialCallRequestList;

    public static SocialResponse fromDto(SocialDto dto){
        var response = new SocialResponse();
        response.setSocialId(String.valueOf(dto.getSocialId()));
        response.setProfileId(String.valueOf(dto.getProfileId()));
        response.setCategoryId(String.valueOf(dto.getCategoryId()));
        response.setType(dto.getType());
        response.setSubject(dto.getSubject());
        response.setDescription(dto.getDescription());
        response.setContact(dto.getContact());
        response.setAlternateContact(dto.getAlternateContact());
        response.setFileId(String.valueOf(dto.getFileId()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        if (dto.getSendTo() != null && !dto.getSendTo().isEmpty()) {
            response.setSendTo(dto.getSendTo()
                    .stream()
                    .map(sendToDto -> SocialSendToResponse.fromDto(sendToDto))
                    .collect(Collectors.toList())
            );
        }
        if (dto.getSocialCallRequestList() != null && !dto.getSocialCallRequestList().isEmpty()) {
            response.setSocialCallRequestList(dto.getSocialCallRequestList()
                    .stream()
                    .map(child -> SocialCallRequestResponse.fromDto(child))
                    .collect(Collectors.toList()));
        }
        return response;
    }
}
