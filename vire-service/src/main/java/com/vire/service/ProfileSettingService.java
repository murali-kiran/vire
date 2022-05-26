package com.vire.service;

import com.vire.model.request.ProfileSettingRequest;
import com.vire.model.response.ProfileSettingResponse;
import com.vire.repository.ProfileSettingRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileSettingService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileSettingRepository profileSettingRepository;

  public ProfileSettingResponse create(final ProfileSettingRequest request) {

    var dto = request.toDto(snowflake);

    return ProfileSettingResponse.fromDto(profileSettingRepository.create(dto));
  }

  public ProfileSettingResponse update(final ProfileSettingRequest request) {

    var dto = request.toDto();

    return ProfileSettingResponse.fromDto(profileSettingRepository.update(dto));
  }

  public ProfileSettingResponse delete(final Long profileSettingId) {

    return profileSettingRepository.delete(profileSettingId)
            .map(dto -> ProfileSettingResponse.fromDto(dto))
            .get();
  }

  public List<ProfileSettingResponse> getAll() {

    return profileSettingRepository
            .getAll()
            .stream()
            .map(dto -> ProfileSettingResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileSettingResponse retrieveById(Long profileSettingId) {

    return profileSettingRepository
            .retrieveById(profileSettingId)
            .map(dto -> ProfileSettingResponse.fromDto(dto))
            .get();
  }

  public List<ProfileSettingResponse> search(final String searchString) {

    return profileSettingRepository
            .search(searchString)
            .stream()
            .map(dto -> ProfileSettingResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
