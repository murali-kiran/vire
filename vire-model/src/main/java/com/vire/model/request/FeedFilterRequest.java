package com.vire.model.request;

import lombok.Data;

import java.util.List;

@Data
public class FeedFilterRequest {

    private String profileId;
    private List<String> communityList;
    private Boolean sendToFollowers;
    //private String location;
}