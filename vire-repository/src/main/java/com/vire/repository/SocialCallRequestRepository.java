package com.vire.repository;

import com.vire.dao.SocialCallRequestDao;
import com.vire.dto.SocialCallRequestDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialCallRequestRepository {

  @Autowired
  SocialCallRequestRepositoryJpa socialCallRequestRepositoryJpa;

  public SocialCallRequestDto create(final SocialCallRequestDto socialCallRequestDto) {

    var socialCallRequestDao = SocialCallRequestDao.fromDto(socialCallRequestDto);
    socialCallRequestDao.onPrePersist();

    return socialCallRequestRepositoryJpa.save(socialCallRequestDao).toDto();
  }

  public SocialCallRequestDto update(final SocialCallRequestDto socialCallRequestDto) {

    var existingObject = socialCallRequestRepositoryJpa.findById(socialCallRequestDto.getSocialCallRequestId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var socialCallRequestDao = SocialCallRequestDao.fromDto(socialCallRequestDto);
    socialCallRequestDao.onPreUpdate();

    return socialCallRequestRepositoryJpa.save(socialCallRequestDao).toDto();
  }

  public Optional<SocialCallRequestDto> delete(final Long socialCallRequestId) {

    var optionalSocial = retrieveById(socialCallRequestId);

    if (optionalSocial.isPresent()) {
      socialCallRequestRepositoryJpa.deleteById(socialCallRequestId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<SocialCallRequestDto> getAll() {

    return socialCallRequestRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<SocialCallRequestDto> retrieveById(Long socialCallRequestId) {

    return socialCallRequestRepositoryJpa.findById(socialCallRequestId).map(dao -> dao.toDto());
  }

  public List<SocialCallRequestDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<SocialCallRequestDao>(searchString).resolve();

    return socialCallRequestRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
