package com.vire.service;

import com.vire.model.request.LocationMasterRequest;
import com.vire.model.response.LocationMasterResponse;
import com.vire.repository.LocationMasterRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationMasterService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  LocationMasterRepository locationMasterRepository;

  public LocationMasterResponse create(final LocationMasterRequest request) {

    var dto = request.toDto(snowflake);

    return LocationMasterResponse.fromDto(locationMasterRepository.create(dto));
  }

  public LocationMasterResponse update(final LocationMasterRequest request) {

    var dto = request.toDto();

    return LocationMasterResponse.fromDto(locationMasterRepository.update(dto));
  }

  public LocationMasterResponse delete(final Long locationMasterId) {

    return locationMasterRepository.delete(locationMasterId)
            .map(dto -> LocationMasterResponse.fromDto(dto))
            .get();
  }

  public List<LocationMasterResponse> getAll() {

    return locationMasterRepository
            .getAll()
            .stream()
            .map(dto -> LocationMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public LocationMasterResponse retrieveById(Long locationMasterId) {

    return locationMasterRepository
            .retrieveById(locationMasterId)
            .map(dto -> LocationMasterResponse.fromDto(dto))
            .get();
  }

  public List<LocationMasterResponse> retrieveDistrictsByState(String state) {

    return locationMasterRepository
            .retrieveDistrictsByState(state)
            .stream()
            .map(dto -> LocationMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
  public List<String> retrieveDistsByState(String state) {
    return locationMasterRepository.retrieveDistsByState(state);
  }
  public List<String> retrieveCitiesByStateAndDist(String state, String district) {
    return locationMasterRepository.retrieveCitiesByStateAndDist(state, district);
  }
  public List<LocationMasterResponse> search(final String searchString) {

    return locationMasterRepository
            .search(searchString)
            .stream()
            .map(dto -> LocationMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
