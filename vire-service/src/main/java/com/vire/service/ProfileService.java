package com.vire.service;


import com.vire.dto.MasterDto;
import com.vire.dto.ProfileDto;
import com.vire.dto.ProfileSettingDto;
import com.vire.exception.VerifyEmailMobileNumberException;
import com.vire.model.request.FirmRequest;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.FirmResponse;
import com.vire.model.response.MinimalProfileResponse;
import com.vire.model.response.PersonalResponse;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.MasterRepository;
import com.vire.repository.ProfileRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// https://stackoverflow.com/questions/56471247/how-to-cache-data-during-application-startup-in-spring-boot-application
@Service
public class ProfileService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileRepository profileRepository;

  @Autowired
  MasterRepository masterRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


  public FirmResponse createFirmProfile(final FirmRequest request) {

    verify(request.getEmailId(),request.getMobileNumber());
    if(request.getProfileSettingTypes() != null)
        request.getProfileSettingTypes().clear();
    var dto = request.toDto();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    dto.setProfileId(snowflake.nextId());
    dto.setFirstLogin("true");
    dto.setProfileWeightage(calculateFirmProfileWeightage(request));
    setProfileSettingTypes(dto, false);
    dto.getFirmProfile().setFirmProfileId(snowflake.nextId());
    dto.getFirmProfile().getAddress().setAddressId(snowflake.nextId());
    return FirmResponse.fromDto(profileRepository.createProfile(dto));
  }

  private void verify(final String email, final String mobileNumber){
    var records = profileRepository.findByEmailIdOrMobileNumber(email, mobileNumber);
    if(!CollectionUtils.isEmpty(records)){
      throw new VerifyEmailMobileNumberException("Email Id or Mobile number already exists in system");
    }
  }
  public PersonalResponse createPersonalProfile(final PersonalRequest request) {

    verify(request.getEmailId(),request.getMobileNumber());
    if(request.getProfileSettingTypes() != null)
        request.getProfileSettingTypes().clear();
    var dto = request.toDto();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    dto.setProfileId(snowflake.nextId());
    dto.setFirstLogin("true");
    dto.setProfileWeightage(calculatePersonalProfileWeightage(request));

    setProfileSettingTypes(dto,true);

    var personalProfileDto = dto.getPersonalProfile();
    personalProfileDto.setPersonalProfileId(snowflake.nextId());
    personalProfileDto.getPermanentAddress().setAddressId(snowflake.nextId());
    personalProfileDto.getPresentAddress().setAddressId(snowflake.nextId());

    if (!CollectionUtils.isEmpty(personalProfileDto.getInterests())) {
      for (var interestDto : personalProfileDto.getInterests()) {
        interestDto.setInterestId(snowflake.nextId());
      }
    }

    return PersonalResponse.fromDto(profileRepository.createProfile(dto));
  }

  public PersonalResponse updatePersonalProfile(final PersonalRequest request) {
    var dto = request.toDto();
    dto.setProfileWeightage(calculatePersonalProfileWeightage(request));
    return PersonalResponse.fromDto(profileRepository.updateProfile(dto));
  }

  public FirmResponse updateFirmProfile(final FirmRequest request) {
    var dto = request.toDto();
    dto.setProfileWeightage(calculateFirmProfileWeightage(request));
    return FirmResponse.fromDto(profileRepository.updateProfile(dto));
  }

  public Optional<PersonalResponse> deletePersonalProfile(final Long profileId) {

    if (!profileRepository.isPersonalProfileExists(profileId)) {
      return Optional.empty();
    } else {
      return profileRepository.deleteProfile(profileId).map(dto -> PersonalResponse.fromDto(dto));
    }

  }

  public Optional<FirmResponse> deleteFirmProfile(final Long profileId) {
    if (!profileRepository.isFirmProfileExists(profileId)) {
      return Optional.empty();
    } else {
      return profileRepository.deleteProfile(profileId).map(dto -> FirmResponse.fromDto(dto));
    }
  }

  public List<PersonalResponse> retrieveAllProfiles() {

    return profileRepository.retrieveAllProfiles().stream()
            .map(dto -> PersonalResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public Optional<PersonalResponse> retrievePersonalProfileById(final Long profileId) {
    if (!profileRepository.isPersonalProfileExists(profileId)) {
      return Optional.empty();
    } else {
      return profileRepository.retrieveProfileById(profileId).map(dto -> PersonalResponse.fromDto(dto));
    }
  }

  public Optional<FirmResponse> retrieveFirmProfileById(final Long profileId) {
    if (!profileRepository.isFirmProfileExists(profileId)) {
      return Optional.empty();
    } else {
      return profileRepository.retrieveProfileById(profileId).map(dto -> FirmResponse.fromDto(dto));
    }
  }

  public List<PersonalResponse> searchProfiles(final String searchString) {
    return profileRepository.searchProfiles(searchString).stream()
            .map(dto -> PersonalResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public Optional<ProfileResponse> retrieveProfileById(final Long profileId) {

    return profileRepository.retrieveProfileById(profileId).map(dto -> {
      var profileResponse =  ProfileResponse.fromDto(dto);
          profileResponse.setType(null);
      return profileResponse;
    });
  }
  
  public MinimalProfileResponse retrieveProfileDtoById(final Long profileId) {

    var dto = profileRepository.retrieveProfileDtoById(profileId);
    return MinimalProfileResponse.fromDto(dto);

  }

  public List<MinimalProfileResponse> retrieveProfilesNotInGivenIds(final List<Long> profileIDs) {

    var dtos = profileRepository.retrieveProfilesNotInGivenIds(profileIDs);
    var profileMinimalDtos = dtos.stream().map(dto->MinimalProfileResponse.fromDto(dto)).collect(Collectors.toList());
    return profileMinimalDtos;

  }

  private void setProfileSettingTypes(ProfileDto profileDto, Boolean isPersonalProfile){
      List<MasterDto> masterDtos = isPersonalProfile? masterRepository.findByMasterType("Personal_Profile_Setting_Type") : masterRepository.findByMasterType("Firm_Profile_Setting_Type");

    if(!CollectionUtils.isEmpty(masterDtos)){
      var profileSettings = new ArrayList<ProfileSettingDto>();
      for(var masterDto : masterDtos){
        var profileSetting = new ProfileSettingDto();
        profileSetting.setProfileSettingId(snowflake.nextId());
        profileSetting.setSettingType(masterDto.getMasterValue());
        profileSetting.setIsEnable(false);
        profileSettings.add(profileSetting);
      }
      profileDto.setProfileSettings(profileSettings);
    }

  }

  private int calculatePersonalProfileWeightage(PersonalRequest request){

    double totalCount = 27;
    double count=0;

    if(!StringUtils.isBlank(request.getName())) count++;
    if(!StringUtils.isBlank(request.getEmailId())) count++;
    if(!StringUtils.isBlank(request.getMobileNumber())) count++;
    if(!StringUtils.isBlank(request.getAadhar())) count++;
    if(!StringUtils.isBlank(request.getIsAadharVerified())) count++;
    if(!StringUtils.isBlank(request.getFileId())) count++;
    if(!StringUtils.isBlank(request.getDateOfBirth())) count++;
    if(!StringUtils.isBlank(request.getGender().name())) count++;

    var personalProfileRequest = request.getPersonalProfile();

    if(!StringUtils.isBlank(personalProfileRequest.getSchoolBoard())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getSchoolName())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getIntermediateBoard())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getIntermediateCollegeName())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getGraduationBoard())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getGraduationCollegeName())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getPostGraduationBoard())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getPostGraduationCollegeName())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getWorkStatus().name())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getFieldProfessionBusiness())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getProductOrService())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getDesignation())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getOrganizationLocation())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getOrganizationName())) count++;
    if(!StringUtils.isBlank(personalProfileRequest.getBloodGroup())) count++;

    if(!StringUtils.isBlank(personalProfileRequest.getBloodDonateWillingness().name())) count++;
    if(personalProfileRequest.getPresentAddress()!=null) count++;
    if(personalProfileRequest.getPermanentAddress()!=null) count++;
    if(!CollectionUtils.isEmpty(personalProfileRequest.getInterests())) count++;
    //double temp = (count/totalCount)*100;
    return (int) ((count/totalCount)*100);
  }


  private int calculateFirmProfileWeightage(FirmRequest request){
    int totalCount = 10;
    int count=0;

    if(!StringUtils.isBlank(request.getName())) count++;
    if(!StringUtils.isBlank(request.getEmailId())) count++;
    if(!StringUtils.isBlank(request.getMobileNumber())) count++;
    if(!StringUtils.isBlank(request.getAadhar())) count++;
    if(!StringUtils.isBlank(request.getIsAadharVerified())) count++;
    if(!StringUtils.isBlank(request.getFileId())) count++;

    var firmProfileRequest = request.getFirmProfile();

    if(!StringUtils.isBlank(firmProfileRequest.getFirmAddress())) count++;
    if(!StringUtils.isBlank(firmProfileRequest.getFieldOfBusiness())) count++;
    if(!StringUtils.isBlank(firmProfileRequest.getProductOrService())) count++;
    if(firmProfileRequest.getAddress()!=null) count++;

    return (count/totalCount)*100;
  }
}