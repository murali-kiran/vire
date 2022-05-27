package com.vire.service;

import com.vire.model.request.SocialCallRequestRequest;
import com.vire.model.response.SocialCallRequestResponse;
import com.vire.repository.SocialCallRequestRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialCallRequestService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialCallRequestRepository socialCallRequestRepository;

  public SocialCallRequestResponse create(final SocialCallRequestRequest request) {

    var dto = request.toDto(snowflake);

    return SocialCallRequestResponse.fromDto(socialCallRequestRepository.create(dto));
  }

  public SocialCallRequestResponse update(final SocialCallRequestRequest request) {

    var dto = request.toDto();

    return SocialCallRequestResponse.fromDto(socialCallRequestRepository.update(dto));
  }

  public SocialCallRequestResponse delete(final Long socialCallRequestId) {

    return socialCallRequestRepository.delete(socialCallRequestId)
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .get();
  }

  public List<SocialCallRequestResponse> getAll() {

    return socialCallRequestRepository
            .getAll()
            .stream()
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialCallRequestResponse retrieveById(Long socialCallRequestId) {

    return socialCallRequestRepository
            .retrieveById(socialCallRequestId)
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .get();
  }

  public List<SocialCallRequestResponse> search(final String searchString) {

    return socialCallRequestRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
