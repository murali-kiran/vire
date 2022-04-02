package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostSentDto implements Serializable {
    private  Long id;
    private  String type;
    private  String value;
}
