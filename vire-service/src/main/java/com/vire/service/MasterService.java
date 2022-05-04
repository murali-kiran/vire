package com.vire.service;

import com.vire.model.request.MasterRequest;
import com.vire.model.response.MasterResponse;
import com.vire.repository.MasterRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  MasterRepository masterRepository;

  public MasterResponse create(final MasterRequest request) {

    var dto = request.toDto(snowflake);

    return MasterResponse.fromDto(masterRepository.create(dto));
  }

  public MasterResponse update(final MasterRequest request) {

    var dto = request.toDto();

    return MasterResponse.fromDto(masterRepository.update(dto));
  }

  public MasterResponse delete(final Long masterId) {

    return masterRepository.delete(masterId)
            .map(dto -> MasterResponse.fromDto(dto))
            .get();
  }

  public List<MasterResponse> getAll() {

    return masterRepository
            .getAll()
            .stream()
            .map(dto -> MasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public MasterResponse retrieveById(Long masterId) {

    return masterRepository
            .retrieveById(masterId)
            .map(dto -> MasterResponse.fromDto(dto))
            .get();
  }

  public List<MasterResponse> search(final String searchString) {

    return masterRepository
            .search(searchString)
            .stream()
            .map(dto -> MasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
