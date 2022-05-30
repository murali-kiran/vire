package com.vire.service;


import com.vire.cacheHandler.GenericCacheHandler;
import com.vire.dao.MasterDao;
import com.vire.dto.MasterDto;
import com.vire.dto.ProfileDto;
import com.vire.dto.ProfileSettingDto;
import com.vire.exception.VerifyEmailMobileNumberException;
import com.vire.model.request.FirmRequest;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.FirmResponse;
import com.vire.model.response.PersonalResponse;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.MasterRepository;
import com.vire.repository.ProfileRepository;
import com.vire.repository.ProfileSettingRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
  GenericCacheHandler genericCacheHandler;

  @Autowired
  PasswordEncoder passwordEncoder;



  public FirmResponse createFirmProfile(final FirmRequest request) {

    verify(request.getEmailId(),request.getMobileNumber());

    var dto = request.toDto();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    dto.setProfileId(snowflake.nextId());
    dto.setFirstLogin("true");
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



    var dto = request.toDto();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    dto.setProfileId(snowflake.nextId());
    dto.setFirstLogin("true");

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
    return PersonalResponse.fromDto(profileRepository.updateProfile(request.toDto()));
  }

  public FirmResponse updateFirmProfile(final FirmRequest request) {
    return FirmResponse.fromDto(profileRepository.updateProfile(request.toDto()));
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

  private void setProfileSettingTypes(ProfileDto profileDto, Boolean isPersonalProfile){
    List<MasterDto> masterDtos = isPersonalProfile? genericCacheHandler.getPersonalProfileSettingTypes() : genericCacheHandler.getFirmProfileSettingTypes();

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
}