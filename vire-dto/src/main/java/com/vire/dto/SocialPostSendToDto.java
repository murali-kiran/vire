package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostSendToDto implements Serializable {
    private  Long sendToId;
    private  String type;
    private  String value;
    private  Long socialId;
}
