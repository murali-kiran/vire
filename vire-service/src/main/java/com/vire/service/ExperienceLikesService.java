package com.vire.service;

import com.vire.model.request.ExperienceLikesRequest;
import com.vire.model.response.ExperienceLikesResponse;
import com.vire.repository.ExperienceLikesRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceLikesService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceLikesRepository experienceLikesRepository;

  public ExperienceLikesResponse create(final ExperienceLikesRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceLikesResponse.fromDto(experienceLikesRepository.create(dto));
  }

  public ExperienceLikesResponse update(final ExperienceLikesRequest request) {

    var dto = request.toDto();

    return ExperienceLikesResponse.fromDto(experienceLikesRepository.update(dto));
  }

  public ExperienceLikesResponse delete(final Long experienceLikesId) {

    return experienceLikesRepository.delete(experienceLikesId)
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceLikesResponse> getAll() {

    return experienceLikesRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceLikesResponse retrieveById(Long experienceLikesId) {

    return experienceLikesRepository
            .retrieveById(experienceLikesId)
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceLikesResponse> search(final String searchString) {

    return experienceLikesRepository
            .search(searchString)
            .stream()
            .map(dto -> ExperienceLikesResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

    public long getLikesCountOfProfile(Long profileId) {
      return experienceLikesRepository.getLikesCountOfProfile(profileId);
    }
}
