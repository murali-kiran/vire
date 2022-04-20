package com.vire.dto;

import com.vire.enumeration.SendToEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialSendToDto implements Serializable {
    private  Long sendToId;
    private String type;
    private  String value;
    private  Long socialId;
    private Long createdTime;
    private Long updatedTime;
}
