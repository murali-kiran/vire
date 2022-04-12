package com.vire.service;

import com.vire.model.request.FirmRequest;
import com.vire.model.request.PersonalRequest;
import com.vire.model.response.FirmResponse;
import com.vire.model.response.PersonalResponse;
import com.vire.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

  @Autowired ProfileRepository ProfileRepository;

  public FirmResponse createFirmProfile(final FirmRequest request) {
    return FirmResponse.fromDto(ProfileRepository.createProfile(request.toDto()));
  }

  public PersonalResponse createPersonalProfile(final PersonalRequest request) {
    return PersonalResponse.fromDto(ProfileRepository.createProfile(request.toDto()));
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
