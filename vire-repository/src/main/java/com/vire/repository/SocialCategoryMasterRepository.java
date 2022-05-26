package com.vire.repository;

import com.vire.dao.SocialCategoryMasterDao;
import com.vire.dto.SocialCategoryMasterDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialCategoryMasterRepository {

  @Autowired
  SocialCategoryMasterRepositoryJpa socialCategoryMasterRepositoryJpa;

  public SocialCategoryMasterDto create(final SocialCategoryMasterDto socialCategoryMasterDto) {

    var socialCategoryMasterDao = SocialCategoryMasterDao.fromDto(socialCategoryMasterDto);
    socialCategoryMasterDao.onPrePersist();

    return socialCategoryMasterRepositoryJpa.save(socialCategoryMasterDao).toDto();
  }

  public SocialCategoryMasterDto update(final SocialCategoryMasterDto socialCategoryMasterDto) {

    var existingObject = socialCategoryMasterRepositoryJpa.findById(socialCategoryMasterDto.getSocialCategoryMasterId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var socialCategoryMasterDao = SocialCategoryMasterDao.fromDto(socialCategoryMasterDto);
    socialCategoryMasterDao.onPreUpdate();

    return socialCategoryMasterRepositoryJpa.save(socialCategoryMasterDao).toDto();
  }

  public Optional<SocialCategoryMasterDto> delete(final Long socialCategoryMasterId) {

    var optionalSocial = retrieveById(socialCategoryMasterId);

    if (optionalSocial.isPresent()) {
      socialCategoryMasterRepositoryJpa.deleteById(socialCategoryMasterId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<SocialCategoryMasterDto> getAll() {

    return socialCategoryMasterRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<SocialCategoryMasterDto> retrieveById(Long socialCategoryMasterId) {

    return socialCategoryMasterRepositoryJpa.findById(socialCategoryMasterId).map(dao -> dao.toDto());
  }

  public List<SocialCategoryMasterDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<SocialCategoryMasterDao>(searchString).resolve();

    return socialCategoryMasterRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
