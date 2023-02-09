package com.vire.repository;

import com.vire.dao.AdminMessageDao;
import com.vire.dao.ChannelDao;
import com.vire.dto.AdminMessageDto;
import com.vire.dto.ChannelDto;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.repository.AdminMessageRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminMessageRepository {

  @Autowired
  AdminMessageRepositoryJpa adminMessageRepositoryJpa;

  public AdminMessageDto create(final AdminMessageDto adminMessageDto) {

    var adminMessageDao = AdminMessageDao.fromDto(adminMessageDto);
    adminMessageDao.onPrePersist();

    return adminMessageRepositoryJpa.save(adminMessageDao).toDto();
  }

  public AdminMessageDto update(final AdminMessageDto adminMessageDto) {

    var existingObject = adminMessageRepositoryJpa.findById(adminMessageDto.getAdminMessageId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var adminMessageDao = AdminMessageDao.fromDto(adminMessageDto);
    adminMessageDao.onPreUpdate();

    return adminMessageRepositoryJpa.save(adminMessageDao).toDto();
  }

  public Optional<AdminMessageDto> delete(final Long adminMessageId) {

    var optionalSocial = retrieveById(adminMessageId);

    if (optionalSocial.isPresent()) {
      adminMessageRepositoryJpa.deleteById(adminMessageId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<AdminMessageDto> getAll() {

    return adminMessageRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<AdminMessageDto> retrieveById(Long adminMessageId) {

    return adminMessageRepositoryJpa.findById(adminMessageId).map(dao -> dao.toDto());
  }

  public List<AdminMessageDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<AdminMessageDao>(searchString).resolve();

    return adminMessageRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

  public PageWiseSearchResponse<AdminMessageDto> getAllPaged(Integer pageNumber, Integer pageSize) {

    PageWiseSearchResponse<AdminMessageDto> response = new PageWiseSearchResponse<>();
    PageRequest request = PageRequest.of(pageNumber-1 , pageSize);

    Page<AdminMessageDao> page = adminMessageRepositoryJpa.findAll(request);
    List<AdminMessageDto> adminMessageDtos = page.stream().map(dao -> dao.toDto()).collect(Collectors.toList());

    response.setPageCount(page.getTotalPages());
    response.setList(adminMessageDtos);

    return response;
  }
}
