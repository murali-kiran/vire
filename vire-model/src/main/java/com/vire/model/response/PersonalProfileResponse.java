package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vire.dto.PersonalProfileDto;
import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.Gender;
import com.vire.enumeration.WorkStatus;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalProfileResponse {

    private String personalProfileId;
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
    private String productOrService;
    private String designation;
    private String organizationName;
    private String organizationLocation;
    private String bloodGroup;
    private BloodDonateWillingness bloodDonateWillingness;
    private AddressResponse presentAddress;
    private AddressResponse permanentAddress;
    private List<PersonalProfileInterestResponse> interests;

    public static PersonalProfileResponse fromDto(final PersonalProfileDto dto) {

        PersonalProfileResponse personalProfileResponse = new PersonalProfileResponse();
        personalProfileResponse.setPersonalProfileId(dto.getPersonalProfileId().toString());
        personalProfileResponse.setSchoolBoard(dto.getSchoolBoard());
        personalProfileResponse.setSchoolName(dto.getSchoolName());
        personalProfileResponse.setIntermediateBoard(dto.getIntermediateBoard());
        personalProfileResponse.setIntermediateCollegeName(dto.getIntermediateCollegeName());
        personalProfileResponse.setGraduationBoard(dto.getGraduationBoard());
        personalProfileResponse.setGraduationCollegeName(dto.getGraduationCollegeName());
        personalProfileResponse.setPostGraduationBoard(dto.getPostGraduationBoard());
        personalProfileResponse.setPostGraduationCollegeName(dto.getPostGraduationCollegeName());
        personalProfileResponse.setWorkStatus(dto.getWorkStatus());
        personalProfileResponse.setFieldProfessionBusiness(dto.getFieldProfessionBusiness());
        personalProfileResponse.setDesignation(dto.getDesignation());
        personalProfileResponse.setOrganizationName(dto.getOrganizationName());
        personalProfileResponse.setOrganizationLocation(dto.getOrganizationLocation());
        personalProfileResponse.setBloodGroup(dto.getBloodGroup());
        personalProfileResponse.setBloodDonateWillingness(dto.getBloodDonateWillingness());

        if(dto.getPresentAddress()!=null)
        personalProfileResponse.setPresentAddress(AddressResponse.fromDto(dto.getPresentAddress()));

        if(dto.getPermanentAddress()!=null)
        personalProfileResponse.setPermanentAddress(AddressResponse.fromDto(dto.getPermanentAddress()));

        personalProfileResponse.setProductOrService(dto.getProductOrService());

        if(dto.getInterests()!=null) {
            personalProfileResponse.setInterests(dto.getInterests().stream().map(interest->PersonalProfileInterestResponse.fromDto(interest)).collect(Collectors.toList()));
        }

        return personalProfileResponse;

    }

}
