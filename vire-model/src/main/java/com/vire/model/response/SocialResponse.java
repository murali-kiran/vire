package com.vire.model.response;

import com.vire.dto.SocialDto;
import com.vire.model.request.SocialPostSentRequest;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

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
    private List<SocialPostSentResponse> sendTo;

    public static SocialResponse fromDto(final SocialDto dto) {
        return new ModelMapper().map(dto,SocialResponse.class);
    }
}
