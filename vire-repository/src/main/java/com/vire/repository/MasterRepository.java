package com.vire.repository;

import com.vire.dao.MasterDao;
import com.vire.dto.MasterDto;
import com.vire.repository.MasterRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MasterRepository {

  @Autowired
  MasterRepositoryJpa masterRepositoryJpa;

  public MasterDto create(final MasterDto masterDto) {

    var masterDao = MasterDao.fromDto(masterDto);
    masterDao.onPrePersist();

    return masterRepositoryJpa.save(masterDao).toDto();
  }

  public MasterDto update(final MasterDto masterDto) {

    var existingObject = masterRepositoryJpa.findById(masterDto.getMasterId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var masterDao = MasterDao.fromDto(masterDto);
    masterDao.onPreUpdate();

    return masterRepositoryJpa.save(masterDao).toDto();
  }

  public Optional<MasterDto> delete(final Long masterId) {

    var optionalSocial = retrieveById(masterId);

    if (optionalSocial.isPresent()) {
      masterRepositoryJpa.deleteById(masterId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<MasterDto> getAll() {

    return masterRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<MasterDto> retrieveById(Long masterId) {

    return masterRepositoryJpa.findById(masterId).map(dao -> dao.toDto());
  }

  public List<MasterDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<MasterDao>(searchString).resolve();

    return masterRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
