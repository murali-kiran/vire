package com.vire.service;

import com.vire.model.request.SocialCategoryMasterRequest;
import com.vire.model.response.SocialCategoryMasterResponse;
import com.vire.repository.SocialCategoryMasterRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialCategoryMasterService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialCategoryMasterRepository socialCategoryMasterRepository;

  public SocialCategoryMasterResponse create(final SocialCategoryMasterRequest request) {

    var dto = request.toDto(snowflake);

    return SocialCategoryMasterResponse.fromDto(socialCategoryMasterRepository.create(dto));
  }

  public SocialCategoryMasterResponse update(final SocialCategoryMasterRequest request) {

    var dto = request.toDto();

    return SocialCategoryMasterResponse.fromDto(socialCategoryMasterRepository.update(dto));
  }

  public SocialCategoryMasterResponse delete(final Long socialCategoryMasterId) {

    return socialCategoryMasterRepository.delete(socialCategoryMasterId)
            .map(dto -> SocialCategoryMasterResponse.fromDto(dto))
            .get();
  }

  public List<SocialCategoryMasterResponse> getAll() {

    return socialCategoryMasterRepository
            .getAll()
            .stream()
            .map(dto -> SocialCategoryMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialCategoryMasterResponse retrieveById(Long socialCategoryMasterId) {

    return socialCategoryMasterRepository
            .retrieveById(socialCategoryMasterId)
            .map(dto -> SocialCategoryMasterResponse.fromDto(dto))
            .get();
  }

  public List<SocialCategoryMasterResponse> search(final String searchString) {

    return socialCategoryMasterRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialCategoryMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
