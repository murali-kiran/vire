package com.vire.service;

import com.vire.model.request.ExperienceReportRequest;
import com.vire.model.response.ExperienceReportResponse;
import com.vire.repository.ExperienceReportRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceReportService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceReportRepository experienceReportRepository;

  public ExperienceReportResponse create(final ExperienceReportRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceReportResponse.fromDto(experienceReportRepository.create(dto));
  }

  public ExperienceReportResponse update(final ExperienceReportRequest request) {

    var dto = request.toDto();

    return ExperienceReportResponse.fromDto(experienceReportRepository.update(dto));
  }

  public ExperienceReportResponse delete(final Long experienceReportId) {

    return experienceReportRepository.delete(experienceReportId)
            .map(dto -> ExperienceReportResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceReportResponse> getAll() {

    return experienceReportRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceReportResponse retrieveById(Long experienceReportId) {

    return experienceReportRepository
            .retrieveById(experienceReportId)
            .map(dto -> ExperienceReportResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceReportResponse> search(final String searchString) {

    return experienceReportRepository
            .search(searchString)
            .stream()
            .map(dto -> ExperienceReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
