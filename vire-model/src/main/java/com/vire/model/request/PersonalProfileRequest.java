package com.vire.model.request;

import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.WorkStatus;
import lombok.Data;

import java.util.List;

@Data
public class PersonalProfileRequest {

    private Long personalProfileId;
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
    private AddressRequest presentAddress;
    private AddressRequest permanentAddress;
    private List<PersonalProfileInterestRequest> interests;

}
