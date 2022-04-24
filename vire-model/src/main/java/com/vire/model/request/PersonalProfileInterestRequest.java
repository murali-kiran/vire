package com.vire.model.request;

import com.vire.dto.PersonalProfileInterestDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class PersonalProfileInterestRequest {

    private String interestId;
    private String interest;

    public PersonalProfileInterestDto toDto(){
        PersonalProfileInterestDto personalProfileInterestDto = new PersonalProfileInterestDto();
        personalProfileInterestDto.setInterestId((this.interestId == null  || !StringUtils.isNumeric(this.interestId))? null : Long.valueOf(this.interestId));
        personalProfileInterestDto.setInterest(interest);

        return personalProfileInterestDto;
    }
}
