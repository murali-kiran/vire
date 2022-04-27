package com.vire.controller;

import com.vire.Application;
import com.vire.enumeration.BloodDonateWillingness;
import com.vire.enumeration.FieldOfBusiness;
import com.vire.enumeration.ProductOrService;
import com.vire.enumeration.WorkStatus;
import com.vire.model.request.*;
import com.vire.repository.ProfileRepositoryJpa;

import com.vire.testing.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class ProfilesControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProfileRepositoryJpa profileRepository;

    @Test
    public void whenValidInput_thenCreatePersonalProfile() throws IOException, Exception {

        int len = 1;

        List<PersonalRequest> personalProfiles =  preparePersonalProfilesRequest(len);
        int idx = 1;
        for(PersonalRequest personalRequest : personalProfiles){
            mvc.perform(post("/api/v1/personalProfile/create").contentType(MediaType.APPLICATION_JSON).
                    content(JsonUtil.toJson(personalRequest))).
                    andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("personalname"+idx)))
                    .andExpect(jsonPath("$.personalProfile.bloodGroup",is("B-ve"+idx)))
                    .andExpect(jsonPath("$.personalProfile.designation",not("Designation"+(idx+1))))
                    .andExpect(jsonPath("$.personalProfile.presentAddress.state",is("personal present TS"+idx)))
                    .andExpect(jsonPath("$.personalProfile.permanentAddress.state",is("personal permanent TS"+idx)));

            idx++;
        }

    }

    @Test
    public void whenValidInput_thenCreateFirmProfile() throws IOException, Exception {

        int len = 1;
        List<FirmRequest> firmProfiles =  createFirmProfilesRequest(len);
        int idx = 1;
        for(FirmRequest firmRequest : firmProfiles){
            mvc.perform(post("/api/v1/firmProfile/create").contentType(MediaType.APPLICATION_JSON).
                            content(JsonUtil.toJson(firmRequest))).
                    andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("firmname"+idx)))
                    .andExpect(jsonPath("$.emailId", is("firmemail"+idx+"gmail.com")))
                    .andExpect(jsonPath("$.firmProfile.fieldOfBusiness",is(FieldOfBusiness.SPORTS.toString())))
                    .andExpect(jsonPath("$.firmProfile.address.state",is("Firm TS"+idx)))
                    .andExpect(jsonPath("$.firmProfile.address.district",is("Medak"+idx)));

            idx++;
        }
    }


    private  List<PersonalRequest> preparePersonalProfilesRequest(int len){

        List<PersonalRequest> personalRequestList = new ArrayList<>();
        for(int i=1;i<=len;i++) {
            PersonalRequest personalRequest = new PersonalRequest();
            personalRequestList.add(personalRequest);

            personalRequest.setName("personalname" + i);
            personalRequest.setPassword("personalpwd");
            personalRequest.setEmailId("personalemail" + i + "gmail.com");
            personalRequest.setMobileNumber("754854128" + i);
            personalRequest.setAadhar("45612378457" + i);
            personalRequest.setIsAadharVerified("yes");

            PersonalProfileRequest personalProfileRequest = new PersonalProfileRequest();
            personalRequest.setPersonalProfile(personalProfileRequest);

            personalProfileRequest.setBloodGroup("B-ve" + i);
            personalProfileRequest.setDesignation("Designation" + i);
            personalProfileRequest.setBloodDonateWillingness(BloodDonateWillingness.NOT_WILLING_TO_DONATE);
            personalProfileRequest.setGraduationBoard("Graduation" + i);
            personalProfileRequest.setGraduationCollegeName("Graduation name" + i);
            personalProfileRequest.setIntermediateBoard("Intermidiate board" + i);
            personalProfileRequest.setIntermediateCollegeName("Intermidiate college" + i);
            personalProfileRequest.setFieldProfessionBusiness("bussiness" + i);
            personalProfileRequest.setOrganizationName("oranization" + i);
            personalProfileRequest.setOrganizationLocation("organizationlocation" + i);
            personalProfileRequest.setSchoolBoard("school" + i);
            personalProfileRequest.setSchoolName("school name" + i);
            personalProfileRequest.setWorkStatus(WorkStatus.JOB_TRAILS);


            List<PersonalProfileInterestRequest> interestRequestList = new ArrayList<>();

            PersonalProfileInterestRequest personalProfileInterestRequest1 = new PersonalProfileInterestRequest();
            personalProfileInterestRequest1.setInterest("sports" + i);

            PersonalProfileInterestRequest personalProfileInterestRequest2 = new PersonalProfileInterestRequest();
            personalProfileInterestRequest2.setInterest("sports" + i);

            interestRequestList.add(personalProfileInterestRequest1);
            interestRequestList.add(personalProfileInterestRequest2);

            personalProfileRequest.setInterests(interestRequestList);

            AddressRequest permanentAddressRequest = new AddressRequest();
            permanentAddressRequest.setCityTownVillage("medak town" + i);
            permanentAddressRequest.setDistrict("Medak" + i);
            permanentAddressRequest.setLatitude(1234.123);
            permanentAddressRequest.setLongitude(3434.1312);
            permanentAddressRequest.setState("personal permanent TS" + i);

            AddressRequest presentAddressRequest = new AddressRequest();
            presentAddressRequest.setCityTownVillage("hyderabad local" + i);
            presentAddressRequest.setDistrict("hyderabad" + i);
            presentAddressRequest.setLatitude(5264.123);
            presentAddressRequest.setLongitude(8834.1312);
            presentAddressRequest.setState("personal present TS" + i);

            personalProfileRequest.setPermanentAddress(permanentAddressRequest);
            personalProfileRequest.setPresentAddress(presentAddressRequest);
        }

        return personalRequestList;

    }

    public List<FirmRequest> createFirmProfilesRequest(int len) {

        List<FirmRequest> firmRequests = new ArrayList<>();
        for(int i=1;i<=len;i++) {
            FirmRequest firmRequest = new FirmRequest();
            firmRequests.add(firmRequest);

            firmRequest.setName("firmname"+i);
            firmRequest.setPassword("firmpwd");
            firmRequest.setEmailId("firmemail"+i+"gmail.com");
            firmRequest.setMobileNumber("754854128"+i);
            firmRequest.setAadhar("45612378457"+i);
            firmRequest.setIsAadharVerified("yes");

            FirmProfileRequest firmProfileRequest = new FirmProfileRequest();
            firmRequest.setFirmProfile(firmProfileRequest);

            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCityTownVillage("medak town"+i);
            addressRequest.setDistrict("Medak"+i);
            addressRequest.setLatitude(1234.123);
            addressRequest.setLongitude(3434.1312);
            addressRequest.setState("Firm TS"+i);

            firmProfileRequest.setAddress(addressRequest);
            firmProfileRequest.setFirmAddress("firm address"+i);
            firmProfileRequest.setFieldOfBusiness(FieldOfBusiness.SPORTS);
            firmProfileRequest.setProductOrService(ProductOrService.SERVICE1);

        }

        return firmRequests;
    }

}



