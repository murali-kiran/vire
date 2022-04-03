package com.vire.model.response;

import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.WorkStatus;
import com.vire.model.request.AddressRequest;
import com.vire.model.request.PersonalProfileInterestRequest;
import lombok.Data;

import java.util.List;

@Data
public class PersonalProfileResponse {

    private Long id;
    private String schoolBoard;
    private String schoolName;
    private String intermediateBoard;
    private String intermediateCollegeName;
    private String graduationBoard;
    private String graduationCollegeName;
    private String postGraduationBoard;
    private String postGraduationCollegeName;
    private WorkStatus workStatus;
    private String fieldProfessionBusiness;
    private String designation;
    private String organizationName;
    private String organizationLocation;
    private String bloodGroup;
    private BloodDonateWillingness bloodDonateWillingness;
    private AddressResponse presentAddress;
    private AddressResponse permanentAddress;
    private List<PersonalProfileInterestRequest> interests;

}
