package com.vire.model.request;

import lombok.Data;

import java.util.List;

@Data
public class SocialFilterRequest {

    private String profileId;
    private List<String> categoryList;
    private List<String> communityList;
    private Integer pageNumber;
    private Integer pageSize;
    //private String location;
}