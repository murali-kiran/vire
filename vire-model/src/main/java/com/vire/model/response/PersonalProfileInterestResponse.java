package com.vire.model.response;

import com.vire.dto.PersonalProfileInterestDto;
import lombok.Data;

@Data
public class PersonalProfileInterestResponse {

    private String interestId;
    private String interest;

    public static PersonalProfileInterestResponse fromDto(final PersonalProfileInterestDto dto){

        PersonalProfileInterestResponse personalProfileInterestResponse = new PersonalProfileInterestResponse();
        personalProfileInterestResponse.setInterestId(dto.getInterestId().toString());
        personalProfileInterestResponse.setInterest(dto.getInterest());

        return  personalProfileInterestResponse;

    }
}
