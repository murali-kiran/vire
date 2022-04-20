package com.vire.model.request;

import com.vire.dto.SocialDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class SocialRequest {

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
    private List<SocialSendToRequest> sendTo;

    public SocialDto toDto() {
        return  new ModelMapper().map(this,SocialDto.class);
    }
}
