package com.vire.service;

import com.vire.model.request.FirmRequest;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.FirmResponse;
import com.vire.model.response.PersonalResponse;
import com.vire.repository.ProfileRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
  @Autowired
  Snowflake snowflake;

  @Autowired ProfileRepository ProfileRepository;

  public FirmResponse createFirmProfile(final FirmRequest request) {
    var dto = request.toDto();
    dto.setProfileId(snowflake.nextId());
    dto.getFirmProfile().setFirmProfileId(snowflake.nextId());
    dto.getFirmProfile().getAddress().setAddressId(snowflake.nextId());
    return FirmResponse.fromDto(ProfileRepository.createProfile(dto));
  }

  public PersonalResponse createPersonalProfile(final PersonalRequest request) {
    var dto = request.toDto();
    dto.setProfileId(snowflake.nextId());

    var personalProfileDto = dto.getPersonalProfile();
    personalProfileDto.setPersonalProfileId(snowflake.nextId());
    personalProfileDto.getPermanentAddress().setAddressId(snowflake.nextId());
    personalProfileDto.getPresentAddress().setAddressId(snowflake.nextId());

    if (!CollectionUtils.isEmpty(personalProfileDto.getInterests())) {
      for (var interestDto : personalProfileDto.getInterests()) {
        interestDto.setInterestId(snowflake.nextId());
      }
    }

    return PersonalResponse.fromDto(ProfileRepository.createProfile(dto));
  }

  public PersonalResponse updatePersonalProfile(final PersonalRequest request) {
    return PersonalResponse.fromDto(ProfileRepository.updateProfile(request.toDto()));
  }

  public FirmResponse updateFirmProfile(final FirmRequest request) {
    return FirmResponse.fromDto(ProfileRepository.updateProfile(request.toDto()));
  }

  public Optional<PersonalResponse> deletePersonalProfile(final Long profileId) {
      return ProfileRepository.deleteProfile(profileId).map(dto -> PersonalResponse.fromDto(dto));
  }

  public Optional<FirmResponse> deleteFirmProfile(final Long profileId) {
    return ProfileRepository.deleteProfile(profileId).map(dto -> FirmResponse.fromDto(dto));
  }

  public List<PersonalResponse> retrieveAllProfiles() {

    return ProfileRepository.retrieveAllProfiles().stream()
        .map(dto -> PersonalResponse.fromDto(dto))
        .collect(Collectors.toList());
  }

  public Optional<PersonalResponse> retrievePersonalProfileById(final Long profileId) {
     return ProfileRepository.retrieveProfileById(profileId).map(dto -> PersonalResponse.fromDto(dto));
  }

  public Optional<FirmResponse> retrieveFirmProfileById(final Long profileId) {
    return ProfileRepository.retrieveProfileById(profileId).map(dto -> FirmResponse.fromDto(dto));
  }

  public List<PersonalResponse> searchProfiles(final String searchString) {
    return ProfileRepository.searchProfiles(searchString).stream()
        .map(dto -> PersonalResponse.fromDto(dto))
        .collect(Collectors.toList());
  }
}
