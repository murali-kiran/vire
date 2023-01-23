package com.vire.service;

import com.vire.model.request.ProfileReportRequest;
import com.vire.model.response.ProfileReportResponse;
import com.vire.repository.ProfileReportRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileReportService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileReportRepository profileReportRepository;

  public ProfileReportResponse create(final ProfileReportRequest request) {

    var dto = request.toDto(snowflake);

    return ProfileReportResponse.fromDto(profileReportRepository.create(dto));
  }

  public ProfileReportResponse update(final ProfileReportRequest request) {

    var dto = request.toDto();

    return ProfileReportResponse.fromDto(profileReportRepository.update(dto));
  }

  public ProfileReportResponse delete(final Long profileReportId) {

    return profileReportRepository.delete(profileReportId)
            .map(dto -> ProfileReportResponse.fromDto(dto))
            .get();
  }

  public List<ProfileReportResponse> getAll() {

    return profileReportRepository
            .getAll()
            .stream()
            .map(dto -> ProfileReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileReportResponse retrieveById(Long profileReportId) {

    return profileReportRepository
            .retrieveById(profileReportId)
            .map(dto -> ProfileReportResponse.fromDto(dto))
            .get();
  }

  public List<ProfileReportResponse> search(final String searchString) {

    return profileReportRepository
            .search(searchString)
            .stream()
            .map(dto -> ProfileReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
