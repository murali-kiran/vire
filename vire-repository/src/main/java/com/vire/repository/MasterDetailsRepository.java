package com.vire.repository;

import com.vire.dao.MasterDetailsDao;
import com.vire.dto.MasterDetailsDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MasterDetailsRepository {

  @Autowired
  MasterDetailsRepositoryJpa masterDetailsRepositoryJpa;

  public MasterDetailsDto create(final MasterDetailsDto masterDetailsDto) {

    var masterDetailsDao = MasterDetailsDao.fromDto(masterDetailsDto);
    masterDetailsDao.onPrePersist();

    return masterDetailsRepositoryJpa.save(masterDetailsDao).toDto();
  }

  public MasterDetailsDto update(final MasterDetailsDto masterDetailsDto) {

    var existingObject = masterDetailsRepositoryJpa.findById(masterDetailsDto.getMasterDetailsId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var masterDetailsDao = MasterDetailsDao.fromDto(masterDetailsDto);
    masterDetailsDao.onPreUpdate();

    return masterDetailsRepositoryJpa.save(masterDetailsDao).toDto();
  }

  public Optional<MasterDetailsDto> delete(final Long masterDetailsId) {

    var optionalSocial = retrieveById(masterDetailsId);

    if (optionalSocial.isPresent()) {
      masterDetailsRepositoryJpa.deleteById(masterDetailsId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<MasterDetailsDto> getAll() {

    return masterDetailsRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<MasterDetailsDto> retrieveById(Long masterDetailsId) {

    return masterDetailsRepositoryJpa.findById(masterDetailsId).map(dao -> dao.toDto());
  }

  public List<MasterDetailsDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<MasterDetailsDao>(searchString).resolve();

    return masterDetailsRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
