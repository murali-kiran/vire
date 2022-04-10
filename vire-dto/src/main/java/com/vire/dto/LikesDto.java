package com.vire.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikesDto implements Serializable {
    private  Long id;
    private  Long likerProfileId;
    private  Long socialPostId;
    private  Long likedTime;
}
