package com.vire.service;

import com.vire.model.request.SocialCategorySentToMasterRequest;
import com.vire.model.response.SocialCategorySentToMasterResponse;
import com.vire.repository.SocialCategorySentToMasterRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialCategorySentToMasterService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialCategorySentToMasterRepository socialCategorySentToMasterRepository;

  public SocialCategorySentToMasterResponse create(final SocialCategorySentToMasterRequest request) {

    var dto = request.toDto(snowflake);

    return SocialCategorySentToMasterResponse.fromDto(socialCategorySentToMasterRepository.create(dto));
  }

  public SocialCategorySentToMasterResponse update(final SocialCategorySentToMasterRequest request) {

    var dto = request.toDto();

    return SocialCategorySentToMasterResponse.fromDto(socialCategorySentToMasterRepository.update(dto));
  }

  public SocialCategorySentToMasterResponse delete(final Long socialCategorySentToMasterId) {

    return socialCategorySentToMasterRepository.delete(socialCategorySentToMasterId)
            .map(dto -> SocialCategorySentToMasterResponse.fromDto(dto))
            .get();
  }

  public List<SocialCategorySentToMasterResponse> getAll() {

    return socialCategorySentToMasterRepository
            .getAll()
            .stream()
            .map(dto -> SocialCategorySentToMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialCategorySentToMasterResponse retrieveById(Long socialCategorySentToMasterId) {

    return socialCategorySentToMasterRepository
            .retrieveById(socialCategorySentToMasterId)
            .map(dto -> SocialCategorySentToMasterResponse.fromDto(dto))
            .get();
  }

  public List<SocialCategorySentToMasterResponse> search(final String searchString) {

    return socialCategorySentToMasterRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialCategorySentToMasterResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
