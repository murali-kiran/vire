package com.vire.service;

import com.vire.model.request.AppRestrictionRequest;
import com.vire.model.response.AppRestrictionResponse;
import com.vire.repository.AppRestrictionRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppRestrictionService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  AppRestrictionRepository appRestrictionRepository;

  public AppRestrictionResponse create(final AppRestrictionRequest request) {

    var dto = request.toDto(snowflake);

    return AppRestrictionResponse.fromDto(appRestrictionRepository.create(dto));
  }

  public AppRestrictionResponse update(final AppRestrictionRequest request) {

    var dto = request.toDto();

    return AppRestrictionResponse.fromDto(appRestrictionRepository.update(dto));
  }

  public AppRestrictionResponse delete(final Long appRestrictionId) {

    return appRestrictionRepository.delete(appRestrictionId)
            .map(dto -> AppRestrictionResponse.fromDto(dto))
            .get();
  }

  public List<AppRestrictionResponse> getAll() {

    return appRestrictionRepository
            .getAll()
            .stream()
            .map(dto -> AppRestrictionResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public AppRestrictionResponse retrieveById(Long appRestrictionId) {

    return appRestrictionRepository
            .retrieveById(appRestrictionId)
            .map(dto -> AppRestrictionResponse.fromDto(dto))
            .get();
  }

  public List<AppRestrictionResponse> search(final String searchString) {

    return appRestrictionRepository
            .search(searchString)
            .stream()
            .map(dto -> AppRestrictionResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
