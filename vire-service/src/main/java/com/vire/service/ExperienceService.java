package com.vire.service;

import com.vire.model.request.ExperienceRequest;
import com.vire.model.response.ExperienceResponse;
import com.vire.repository.ExperienceRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceRepository experienceRepository;

  public ExperienceResponse create(final ExperienceRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceResponse.fromDto(experienceRepository.create(dto));
  }

  public ExperienceResponse update(final ExperienceRequest request) {

    var dto = request.toDto();

    return ExperienceResponse.fromDto(experienceRepository.update(dto));
  }

  public ExperienceResponse delete(final Long experienceId) {

    return experienceRepository.delete(experienceId)
            .map(dto -> ExperienceResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceResponse> getAll() {

    return experienceRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceResponse retrieveById(Long experienceId) {

    return experienceRepository
            .retrieveById(experienceId)
            .map(dto -> ExperienceResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceResponse> search(final String searchString) {

    return experienceRepository
            .search(searchString)
            .stream()
            .map(dto -> ExperienceResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
