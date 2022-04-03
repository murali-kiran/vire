package com.vire.service;

import com.vire.dto.ProfileDto;
import com.vire.model.request.ProfileRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

  @Autowired ProfileRepository ProfileRepository;

  public ProfileResponse createProfile(final ProfileRequest request) {
    return ProfileResponse.fromDto(ProfileRepository.createProfile(request.toDto()));
  }

  public ProfileResponse updateProfile(final ProfileRequest request) {
    return ProfileResponse.fromDto(ProfileRepository.updateProfile(request.toDto()));
  }

  public Optional<ProfileResponse> deleteProfile(final Long profileId) {
      return ProfileRepository.deleteProfile(profileId).map(dto -> ProfileResponse.fromDto(dto));
  }

  public List<ProfileResponse> retrieveAllProfiles() {

    return ProfileRepository.retrieveAllProfiles().stream()
        .map(dto -> ProfileResponse.fromDto(dto))
        .collect(Collectors.toList());
  }

  public Optional<ProfileResponse> retrieveProfileById(final Long profileId) {
     return ProfileRepository.retrieveProfileById(profileId).map(dto -> ProfileResponse.fromDto(dto));
  }

  public List<ProfileResponse> searchProfiles(final String searchString) {
    return ProfileRepository.searchProfiles(searchString).stream()
        .map(dto -> ProfileResponse.fromDto(dto))
        .collect(Collectors.toList());
  }
}
