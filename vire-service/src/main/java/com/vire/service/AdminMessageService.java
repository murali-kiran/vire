package com.vire.service;

import com.vire.dto.AdminMessageDto;
import com.vire.dto.ChannelDto;
import com.vire.model.request.AdminMessageRequest;
import com.vire.model.response.AdminMessageResponse;
import com.vire.model.response.ChannelResponse;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.repository.AdminMessageRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMessageService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  AdminMessageRepository adminMessageRepository;

  public AdminMessageResponse create(final AdminMessageRequest request) {

    var dto = request.toDto(snowflake);

    return AdminMessageResponse.fromDto(adminMessageRepository.create(dto));
  }

  public AdminMessageResponse update(final AdminMessageRequest request) {

    var dto = request.toDto();

    return AdminMessageResponse.fromDto(adminMessageRepository.update(dto));
  }

  public AdminMessageResponse delete(final Long adminMessageId) {

    return adminMessageRepository.delete(adminMessageId)
            .map(dto -> AdminMessageResponse.fromDto(dto))
            .get();
  }

  public List<AdminMessageResponse> getAll() {

    return adminMessageRepository
            .getAll()
            .stream()
            .map(dto -> AdminMessageResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public AdminMessageResponse retrieveById(Long adminMessageId) {

    return adminMessageRepository
            .retrieveById(adminMessageId)
            .map(dto -> AdminMessageResponse.fromDto(dto))
            .get();
  }

  public List<AdminMessageResponse> search(final String searchString) {

    return adminMessageRepository
            .search(searchString)
            .stream()
            .map(dto -> AdminMessageResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public PageWiseSearchResponse<AdminMessageResponse> getAllPaged(Integer pageNumber, Integer pageSize) {

    PageWiseSearchResponse<AdminMessageDto> adminMsgResponse = adminMessageRepository.getAllPaged(pageNumber,pageSize);
    List<AdminMessageResponse> adminMsgResponses = adminMsgResponse.getList().parallelStream()
            .map(dto -> AdminMessageResponse.fromDto(dto))
            .collect(Collectors.toList());

    PageWiseSearchResponse<AdminMessageResponse> response = new PageWiseSearchResponse<>();
    response.setPageCount(adminMsgResponse.getPageCount());
    response.setList(adminMsgResponses);

    return response;
  }
}
