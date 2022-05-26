package com.vire.repository;

import com.vire.dao.SocialCategoryTypeMasterDao;
import com.vire.dto.SocialCategoryTypeMasterDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialCategoryTypeMasterRepository {

  @Autowired
  SocialCategoryTypeMasterRepositoryJpa socialCategoryTypeMasterRepositoryJpa;

  public SocialCategoryTypeMasterDto create(final SocialCategoryTypeMasterDto socialCategoryTypeMasterDto) {

    var socialCategoryTypeMasterDao = SocialCategoryTypeMasterDao.fromDto(socialCategoryTypeMasterDto);
    socialCategoryTypeMasterDao.onPrePersist();

    return socialCategoryTypeMasterRepositoryJpa.save(socialCategoryTypeMasterDao).toDto();
  }

  public SocialCategoryTypeMasterDto update(final SocialCategoryTypeMasterDto socialCategoryTypeMasterDto) {

    var existingObject = socialCategoryTypeMasterRepositoryJpa.findById(socialCategoryTypeMasterDto.getSocialCategoryTypeMasterId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var socialCategoryTypeMasterDao = SocialCategoryTypeMasterDao.fromDto(socialCategoryTypeMasterDto);
    socialCategoryTypeMasterDao.onPreUpdate();

    return socialCategoryTypeMasterRepositoryJpa.save(socialCategoryTypeMasterDao).toDto();
  }

  public Optional<SocialCategoryTypeMasterDto> delete(final Long socialCategoryTypeMasterId) {

    var optionalSocial = retrieveById(socialCategoryTypeMasterId);

    if (optionalSocial.isPresent()) {
      socialCategoryTypeMasterRepositoryJpa.deleteById(socialCategoryTypeMasterId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<SocialCategoryTypeMasterDto> getAll() {

    return socialCategoryTypeMasterRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<SocialCategoryTypeMasterDto> retrieveById(Long socialCategoryTypeMasterId) {

    return socialCategoryTypeMasterRepositoryJpa.findById(socialCategoryTypeMasterId).map(dao -> dao.toDto());
  }

  public List<SocialCategoryTypeMasterDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<SocialCategoryTypeMasterDao>(searchString).resolve();

    return socialCategoryTypeMasterRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
