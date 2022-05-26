package com.vire.service;

import com.vire.model.request.SocialCategoryTypeMasterRequest;
import com.vire.model.response.SocialCategoryTypeMasterResponse;
import com.vire.repository.SocialCategoryTypeMasterRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialCategoryTypeMasterService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialCategoryTypeMasterRepository socialCategoryTypeMasterRepository;

  public SocialCategoryTypeMasterResponse create(final SocialCategoryTypeMasterRequest request) {

    var dto = request.toDto(snowflake);

    return SocialCategoryTypeMasterResponse.fromDto(socialCategoryTypeMasterRepository.create(dto));
  }

  public SocialCategoryTypeMasterResponse update(final SocialCategoryTypeMasterRequest request) {

    var dto = request.toDto();

    return SocialCategoryTypeMasterResponse.fromDto(socialCategoryTypeMasterRepository.update(dto));
  }

  public SocialCategoryTypeMasterResponse delete(final Long socialCategoryTypeMasterId) {

    return socialCategoryTypeMasterRepository.delete(socialCategoryTypeMasterId)
            .map(dto -> SocialCategoryTypeMasterResponse.fromDto(dto))
            .get();
  }

  public List<SocialCategoryTypeMasterResponse> getAll() {

    return socialCategoryTypeMasterRepository
            .getAll()
            .stream()
            .map(dto -> SocialCategoryTypeMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialCategoryTypeMasterResponse retrieveById(Long socialCategoryTypeMasterId) {

    return socialCategoryTypeMasterRepository
            .retrieveById(socialCategoryTypeMasterId)
            .map(dto -> SocialCategoryTypeMasterResponse.fromDto(dto))
            .get();
  }

  public List<SocialCategoryTypeMasterResponse> search(final String searchString) {

    return socialCategoryTypeMasterRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialCategoryTypeMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
