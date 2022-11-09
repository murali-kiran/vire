package com.vire.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ExperienceFilterRequest {

    private String profileId;
    private List<Long> categoryList;
    private Integer pageNumber;
    private Integer pageSize;

}