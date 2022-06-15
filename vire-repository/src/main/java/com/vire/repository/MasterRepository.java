package com.vire.repository;

import com.vire.dao.MasterDao;
import com.vire.dto.MasterDto;
import com.vire.dto.PagedResponseDto;
import com.vire.repository.MasterRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public PagedResponseDto<MasterDto> getAllPaged(Integer pageNumber, Integer pageSize) {

    PageRequest request = PageRequest.of(pageNumber-1 , pageSize);
     var machedResult = masterRepositoryJpa.findAll(request);

     var masterDtoList = new ArrayList<MasterDto>();
     machedResult.forEach(dao -> masterDtoList.add(dao.toDto()));

    var pagedDto = new PagedResponseDto<>(pageNumber, pageSize, masterDtoList,
            machedResult.getTotalElements(), machedResult.getTotalPages());

    return pagedDto;
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

  @Cacheable(value="masterDto", key="#masterType")
  public List<MasterDto> findByMasterType(String masterType) {
    return masterRepositoryJpa.findByMasterType(masterType).stream()
            .map(dao->dao.toDto())
            .collect(Collectors.toList());
  }

}
