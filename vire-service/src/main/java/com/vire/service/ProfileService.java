package com.vire.service;


import com.vire.dao.ProfileDao;
import com.vire.dto.*;
import com.vire.exception.VerifyEmailMobileNumberException;
import com.vire.model.request.FirmRequest;
import com.vire.model.request.PersonalRequest;
import com.vire.model.request.UpdateEmailRequest;
import com.vire.model.request.UpdatePasswordRequest;
import com.vire.model.response.*;
import com.vire.repository.MasterRepository;
import com.vire.repository.ProfileRepository;
import com.vire.utils.Snowflake;
import com.vire.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

  @Autowired
  AuthenticationManager authenticationManager;


  public FirmResponse createFirmProfile(final FirmRequest request) {

    verify(request.getEmailId(),request.getMobileNumber());
    if(request.getProfileSettingTypes() != null)
        request.getProfileSettingTypes().clear();
    var dto = request.toDto();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    dto.setProfileId(snowflake.nextId());
    dto.setFirstLogin("true");
    dto.setProfileWeightage(calculateFirmProfileWeightage(request));
    dto.setProfileType("firm");
    dto.setProfileStatus("active");
    setProfileSettingTypes(dto, false);

    var firmProfile = dto.getFirmProfile();

    if(firmProfile == null){
      firmProfile = new FirmProfileDto();
      dto.setFirmProfile(firmProfile);
    }
    firmProfile.setFirmProfileId(snowflake.nextId());

    if(firmProfile.getAddress()!=null)
      firmProfile.getAddress().setAddressId(snowflake.nextId());

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
    dto.setProfileType("personal");
    dto.setProfileStatus("active");
    setProfileSettingTypes(dto,true);

    var personalProfileDto = dto.getPersonalProfile();
    if(personalProfileDto == null){
      personalProfileDto = new PersonalProfileDto();
      dto.setPersonalProfile(personalProfileDto);
    }

    personalProfileDto.setPersonalProfileId(snowflake.nextId());

    if(personalProfileDto.getPermanentAddress()!=null)
    personalProfileDto.getPermanentAddress().setAddressId(snowflake.nextId());

    if(personalProfileDto.getPresentAddress()!=null)
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

  public List<ProfileResponse> searchAnyProfiles(final String searchString) {
        return profileRepository.searchProfiles(searchString).stream()
                .map(dto -> ProfileResponse.fromDto(dto))
                .collect(Collectors.toList());
  }

  public PageWiseSearchResponse<ProfileResponse> searchAnyProfilesPaged(final String searchString,Integer pageNumber,Integer pageSize) {

      PageWiseSearchResponse<ProfileDto> searchResponse = profileRepository.searchProfilesPaged(searchString,pageNumber,pageSize);
      List<ProfileDto> profileDtos = searchResponse.getList();

      List<ProfileResponse> profileResponses = profileDtos.stream()
                .map(dto -> ProfileResponse.fromDto(dto))
                .collect(Collectors.toList());

      PageWiseSearchResponse<ProfileResponse> response = new PageWiseSearchResponse<ProfileResponse>();
      response.setPageCount(searchResponse.getPageCount());
      response.setList(profileResponses);

      return response;
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
    if(request.getIsAadharVerified()!=null) count++;
    if(!StringUtils.isBlank(request.getFileId())) count++;
    if(!StringUtils.isBlank(request.getDateOfBirth())) count++;
    if(request.getGender()!=null) count++;

    var personalProfileRequest = request.getPersonalProfile();

    if(personalProfileRequest!=null) {
      if (!StringUtils.isBlank(personalProfileRequest.getSchoolBoard())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getSchoolName())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getIntermediateBoard())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getIntermediateCollegeName())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getGraduationBoard())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getGraduationCollegeName())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getPostGraduationBoard())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getPostGraduationCollegeName())) count++;
      if (personalProfileRequest.getWorkStatus() != null) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getFieldProfessionBusiness())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getProductOrService())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getDesignation())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getOrganizationLocation())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getOrganizationName())) count++;
      if (!StringUtils.isBlank(personalProfileRequest.getBloodGroup())) count++;

            if (personalProfileRequest.getBloodDonateWillingness() != null) count++;
            if (personalProfileRequest.getPresentAddress() != null) count++;
            if (personalProfileRequest.getPermanentAddress() != null) count++;
            if (!CollectionUtils.isEmpty(personalProfileRequest.getInterests())) count++;
        }
        //double temp = (count/totalCount)*100;
        return (int) ((count / totalCount) * 100);
    }


  private int calculateFirmProfileWeightage(FirmRequest request){
    int totalCount = 10;
    int count=0;

    if(!StringUtils.isBlank(request.getName())) count++;
    if(!StringUtils.isBlank(request.getEmailId())) count++;
    if(!StringUtils.isBlank(request.getMobileNumber())) count++;
    if(!StringUtils.isBlank(request.getAadhar())) count++;
    if(request.getIsAadharVerified()!=null) count++;
    if(!StringUtils.isBlank(request.getFileId())) count++;

    var firmProfileRequest = request.getFirmProfile();

    if(firmProfileRequest!=null) {
      if (!StringUtils.isBlank(firmProfileRequest.getFirmAddress())) count++;
      if (!StringUtils.isBlank(firmProfileRequest.getFieldOfBusiness())) count++;
      if (!StringUtils.isBlank(firmProfileRequest.getProductOrService())) count++;
      if (firmProfileRequest.getAddress() != null) count++;
    }

    return (count/totalCount)*100;
  }

    @Transactional
    public Optional<UpdatePasswordResponse> updatePassword(UpdatePasswordRequest updatePasswordRequest) {

        UpdatePasswordResponse updatePasswordResponse = null;
        boolean isEmail = false;
        String emailOrPassword = updatePasswordRequest.getEmailOrphonenumber();
        var profile = profileRepository.retrieveProfileById(updatePasswordRequest.getProfileId() == null ? null : Long.valueOf(updatePasswordRequest.getProfileId()));

        if (profile.isPresent()) {
            var profileDto = profile.get();
            if (Utility.isEmailValid(emailOrPassword) && profileDto.getEmailId().equalsIgnoreCase(emailOrPassword)) {
                isEmail = true;
            } else if (Utility.isPhoneNumberValid(emailOrPassword) && profileDto.getMobileNumber().equalsIgnoreCase(emailOrPassword)) {
                isEmail = false;
            } else {
                updatePasswordResponse = new UpdatePasswordResponse(false, "Invalid email or phone number");
                return Optional.of(updatePasswordResponse);
            }

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(emailOrPassword, updatePasswordRequest.getOldPassword()));

                if (isEmail)
                    profileRepository.updatePasswordViaEmailAndProfileId(profileDto.getProfileId(),emailOrPassword, passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
                else
                    profileRepository.updatePasswordViaMobileNumberAndProfileId(profileDto.getProfileId(),emailOrPassword, passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

                updatePasswordResponse = new UpdatePasswordResponse(true, "Password is updated");
                return Optional.of(updatePasswordResponse);

            } catch (Exception e) {
                e.printStackTrace();
                updatePasswordResponse = new UpdatePasswordResponse(false, "Existing password does not match");
                return Optional.of(updatePasswordResponse);
            }

        } else {
            updatePasswordResponse = new UpdatePasswordResponse(false, "Profile with this ID does not exists");
            return Optional.of(updatePasswordResponse);
        }

    }

    @Transactional
    public Optional<UpdateEmailResponse> updateEmail(UpdateEmailRequest updateEmailRequest) {

        UpdateEmailResponse updateEmailResponse = null;
        boolean isEmail = false;
        String emailOrPassword = updateEmailRequest.getEmailOrphonenumber();
        Long profileIdL = updateEmailRequest.getProfileId() == null ? null : Long.valueOf(updateEmailRequest.getProfileId());
        var profile = profileRepository.retrieveProfileById(profileIdL);

        if (profile.isPresent()) {
            var profileDto = profile.get();
            if (Utility.isEmailValid(emailOrPassword) && profileDto.getEmailId().equalsIgnoreCase(emailOrPassword)) {
                isEmail = true;
            } else if (Utility.isPhoneNumberValid(emailOrPassword) && profileDto.getMobileNumber().equalsIgnoreCase(emailOrPassword)) {
                isEmail = false;
            } else {
                updateEmailResponse = new UpdateEmailResponse(false, "Invalid email or phone number");
                return Optional.of(updateEmailResponse);
            }
            Optional<ProfileDao> newProfile = profileRepository.findByEmailId(updateEmailRequest.getNewEmail());

            if (newProfile.isPresent()) {
                updateEmailResponse = new UpdateEmailResponse(false, "Profile already exists with new  Email ");
                return Optional.of(updateEmailResponse);
            } else {

                try {

                    if (isEmail)
                        profileRepository.updateEmailViaOldEmailAndProfileId(profileIdL, updateEmailRequest.getEmailOrphonenumber(), updateEmailRequest.getNewEmail());
                    else
                        profileRepository.updateEmailViaMobileNumberAndProfileId(profileIdL, updateEmailRequest.getEmailOrphonenumber(), updateEmailRequest.getNewEmail());

                    updateEmailResponse = new UpdateEmailResponse(true, "Email is updated");
                    return Optional.of(updateEmailResponse);

                } catch (Exception e) {
                    e.printStackTrace();
                    updateEmailResponse = new UpdateEmailResponse(false, "Email updation failed :: " + e.getMessage());
                    return Optional.of(updateEmailResponse);
                }

            }
        } else {
            updateEmailResponse = new UpdateEmailResponse(false, "Profile with this ID does not exists");
            return Optional.of(updateEmailResponse);
        }

    }

    public ProfileResponse updateProfileSettingByProfileId(Long profileId, Boolean isPrivate, Boolean showFriends) {
      return ProfileResponse.fromDto(profileRepository.updateProfileSetting(profileId, isPrivate, showFriends));
    }
}
