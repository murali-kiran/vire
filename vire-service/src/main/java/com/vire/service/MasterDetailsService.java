package com.vire.service;

import com.vire.model.request.MasterDetailsRequest;
import com.vire.model.response.MasterDetailsResponse;
import com.vire.repository.MasterDetailsRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterDetailsService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  MasterDetailsRepository masterDetailsRepository;

  public MasterDetailsResponse create(final MasterDetailsRequest request) {

    var dto = request.toDto(snowflake);

    return MasterDetailsResponse.fromDto(masterDetailsRepository.create(dto));
  }

  public MasterDetailsResponse update(final MasterDetailsRequest request) {

    var dto = request.toDto();

    return MasterDetailsResponse.fromDto(masterDetailsRepository.update(dto));
  }

  public MasterDetailsResponse delete(final Long masterDetailsId) {

    return masterDetailsRepository.delete(masterDetailsId)
            .map(dto -> MasterDetailsResponse.fromDto(dto))
            .get();
  }

  public List<MasterDetailsResponse> getAll() {

    return masterDetailsRepository
            .getAll()
            .stream()
            .map(dto -> MasterDetailsResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public MasterDetailsResponse retrieveById(Long masterDetailsId) {

    return masterDetailsRepository
            .retrieveById(masterDetailsId)
            .map(dto -> MasterDetailsResponse.fromDto(dto))
            .get();
  }

  public List<MasterDetailsResponse> search(final String searchString) {

    return masterDetailsRepository
            .search(searchString)
            .stream()
            .map(dto -> MasterDetailsResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
