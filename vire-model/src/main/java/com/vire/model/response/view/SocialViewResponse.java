package com.vire.model.response.view;

import com.vire.dto.view.FeedsViewDto;
import com.vire.dto.view.SocialViewDto;

public class SocialViewResponse {


    public static SocialViewResponse fromDto(SocialViewDto dto){

        var response = new SocialViewResponse();
        return response;
    }
}
