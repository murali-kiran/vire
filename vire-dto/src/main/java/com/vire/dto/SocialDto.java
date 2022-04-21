package com.vire.dto;

import lombok.Data;

import java.util.List;

@Data
public class SocialDto{
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
    private  List<SocialSendToDto> sendTo;
}
