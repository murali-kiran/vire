package com.vire.service;

import com.vire.model.request.SocialReportRequest;
import com.vire.model.response.SocialReportResponse;
import com.vire.repository.SocialReportRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialReportService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialReportRepository socialReportRepository;

  public SocialReportResponse create(final SocialReportRequest request) {

    var dto = request.toDto(snowflake);

    return SocialReportResponse.fromDto(socialReportRepository.create(dto));
  }

  public SocialReportResponse update(final SocialReportRequest request) {

    var dto = request.toDto();

    return SocialReportResponse.fromDto(socialReportRepository.update(dto));
  }

  public SocialReportResponse delete(final Long socialReportId) {

    return socialReportRepository.delete(socialReportId)
            .map(dto -> SocialReportResponse.fromDto(dto))
            .get();
  }

  public List<SocialReportResponse> getAll() {

    return socialReportRepository
            .getAll()
            .stream()
            .map(dto -> SocialReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialReportResponse retrieveById(Long socialReportId) {

    return socialReportRepository
            .retrieveById(socialReportId)
            .map(dto -> SocialReportResponse.fromDto(dto))
            .get();
  }

  public List<SocialReportResponse> search(final String searchString) {

    return socialReportRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
