package com.vire.repository;

import com.vire.dao.SocialCategorySentToMasterDao;
import com.vire.dto.SocialCategorySentToMasterDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialCategorySentToMasterRepository {

  @Autowired
  SocialCategorySentToMasterRepositoryJpa socialCategorySentToMasterRepositoryJpa;

  public SocialCategorySentToMasterDto create(final SocialCategorySentToMasterDto socialCategorySentToMasterDto) {

    var socialCategorySentToMasterDao = SocialCategorySentToMasterDao.fromDto(socialCategorySentToMasterDto);
    socialCategorySentToMasterDao.onPrePersist();

    return socialCategorySentToMasterRepositoryJpa.save(socialCategorySentToMasterDao).toDto();
  }

  public SocialCategorySentToMasterDto update(final SocialCategorySentToMasterDto socialCategorySentToMasterDto) {

    var existingObject = socialCategorySentToMasterRepositoryJpa.findById(socialCategorySentToMasterDto.getSocialCategorySentToMasterId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var socialCategorySentToMasterDao = SocialCategorySentToMasterDao.fromDto(socialCategorySentToMasterDto);
    socialCategorySentToMasterDao.onPreUpdate();

    return socialCategorySentToMasterRepositoryJpa.save(socialCategorySentToMasterDao).toDto();
  }

  public Optional<SocialCategorySentToMasterDto> delete(final Long socialCategorySentToMasterId) {

    var optionalSocial = retrieveById(socialCategorySentToMasterId);

    if (optionalSocial.isPresent()) {
      socialCategorySentToMasterRepositoryJpa.deleteById(socialCategorySentToMasterId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<SocialCategorySentToMasterDto> getAll() {

    return socialCategorySentToMasterRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<SocialCategorySentToMasterDto> retrieveById(Long socialCategorySentToMasterId) {

    return socialCategorySentToMasterRepositoryJpa.findById(socialCategorySentToMasterId).map(dao -> dao.toDto());
  }

  public List<SocialCategorySentToMasterDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<SocialCategorySentToMasterDao>(searchString).resolve();

    return socialCategorySentToMasterRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
