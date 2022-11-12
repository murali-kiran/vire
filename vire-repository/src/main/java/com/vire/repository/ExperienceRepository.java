package com.vire.repository;

import com.vire.dao.*;
import com.vire.dto.ExperienceDto;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.repository.search.CustomSpecificationResolver;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.vire.dao.view.ExperienceViewDao;
import com.vire.repository.view.ExperienceViewRepositoryJpa;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExperienceRepository {

  @PersistenceContext
  EntityManager entityManager;
  @Autowired
  ExperienceRepositoryJpa experienceRepositoryJpa;
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  ExperienceViewsCountRepositoryJpa experienceViewsCountRepositoryJpa;
  @Autowired
  ProfileRepositoryJpa profileRepositoryJpa;
  @Autowired
  ExperinceFileRepositoryJpa experienceFileRepositoryJpa;
  @Autowired
  ExperienceViewRepositoryJpa experienceViewRepositoryJpa;


  private static final String BASIC_QUERY = "SELECT e.* FROM experience e "+
  "WHERE ( e.category_id IN (%s) ";
  private static final String COMMON_WHERE = " %s = '%s'";
  private static final String SEND_TO_TYPE_LOCATION_CITY = "location_city";
  private static final String SEND_TO_TYPE_LOCATION_DIST = "location_district";
  private static final String SEND_TO_TYPE_LOCATION_STATE = "location_state";
  @Transactional
  public ExperienceDto create(final ExperienceDto experienceDto) {

    var experienceDao = ExperienceDao.fromDto(experienceDto);
    experienceDao.onPrePersist();
    return experienceRepositoryJpa.save(experienceDao).toDto();
  }

  public ExperienceDto update(final ExperienceDto experienceDto) {

    var files = experienceFileRepositoryJpa.findByExperienceId(experienceDto.getExperienceId());
    var fileIds = files.stream().map(f -> f.getExperienceFileId()).collect(Collectors.toList());
    for(var fileid: fileIds){
      experienceFileRepositoryJpa.deleteById(fileid);
    }

    var existingObject = experienceRepositoryJpa.findById(experienceDto.getExperienceId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var experienceDao = existingObject.get();
    experienceDao.setDescription(experienceDto.getDescription());
    experienceDao.setTitle(experienceDto.getTitle());
    if (experienceDto.getExperienceFileList() != null && !experienceDto.getExperienceFileList().isEmpty()) {
      experienceDao.setExperienceFileList(experienceDto.getExperienceFileList()
              .stream()
              .map(experienceFileDto -> ExperienceFileDao.fromDto(experienceFileDto))
              .collect(Collectors.toList())
      );
    }
    if (!CollectionUtils.isEmpty(experienceDao.getExperienceFileList())) {
      for (var experienceFile : experienceDao.getExperienceFileList()) {
        experienceFile.setExperience(experienceDao);
        experienceFile.onPrePersist();
      }
    }
    experienceDao.onPreUpdate();

    return experienceRepositoryJpa.save(experienceDao).toDto();
  }

  @Transactional
  public Optional<ExperienceDto> delete(final Long experienceId) {

    var optionalSocial = retrieveById(experienceId);

    if (optionalSocial.isPresent()) {
      //experienceRepositoryJpa.deleteById(experienceId);
      experienceRepositoryJpa.updateDeletedTime(Instant.now().toEpochMilli(), experienceId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  /*public List<ExperienceDto> getAll(Pageable pageable) {

    return experienceRepositoryJpa.findAllPaged(Sort.by(Sort.Direction.DESC, "updatedTime"), pageable)
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }*/
  public List<ExperienceDto> getAll() {

    return experienceRepositoryJpa.findAll(Sort.by(Sort.Direction.DESC, "updatedTime"))
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

  public List<ExperienceDto> retreveByFilterList(List<Long> categoryList) {

    return experienceRepositoryJpa.findByCategoryList(categoryList)
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ExperienceDto> retrieveById(Long experienceId) {

    return experienceRepositoryJpa.findById(experienceId).map(dao -> dao.toDto());
  }

  public List<ExperienceDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ExperienceDao>(searchString).resolve();

    return experienceRepositoryJpa.findAll(spec,Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

    public List<ExperienceDao> getExperienceListByProfile(Long profileId, int pageNumber, int pageSize, List<String> categoryFiltersToBeApplied) {

      ProfileDao profileDao = profileRepositoryJpa.findById(profileId).get();
      StringBuffer query = new StringBuffer();
      AddressDao address = null;

      StringBuffer categoryQuery = new StringBuffer("select m.master_id from master m where " );
      if(profileDao.getPersonalProfile()!=null) {
        address = profileDao.getPersonalProfile().getPresentAddress();
        var designation = profileDao.getPersonalProfile().getDesignation();
        var fieldProfessionBusiness = profileDao.getPersonalProfile().getFieldProfessionBusiness();

        if(StringUtils.isEmpty(designation) && StringUtils.isEmpty(fieldProfessionBusiness)){
          return new ArrayList<>();
        }

        StringBuffer sb = new StringBuffer("");
        if(!StringUtils.isEmpty(designation)){
          sb.append("(m.master_type = 'Designation' AND m.master_value= '"+designation+"')");
        }

        if(!StringUtils.isEmpty(fieldProfessionBusiness)){
         if(!sb.toString().isEmpty()){
           sb.append(" OR ");
         }
          sb.append("(m.master_type = 'Field_Profession_Business' AND m.master_value= '"+fieldProfessionBusiness+"')");
        }
        categoryQuery.append(sb.toString());
      }else{
        address = profileDao.getFirmProfile().getAddress();
        if(StringUtils.isEmpty(profileDao.getFirmProfile().getFieldOfBusiness())){
          return new ArrayList<>();
        }else{
          categoryQuery.append("(m.master_type = 'Field_Profession_Business' " +
                  "AND m.master_value= '"+profileDao.getFirmProfile().getFieldOfBusiness()+"')");
        }
      }

      query.append(frameAddressQuery(categoryQuery.toString(), address));
      query.append(" ) OR e.profile_id="+profileDao.getProfileId());
      query.append(" ORDER BY e.updated_time DESC ");
      log.info("Generated Query is {}", query.toString());
      var queryObject = entityManager.createNativeQuery(query.toString(), ExperienceDao.class);
      queryObject.setFirstResult((pageNumber - 1) * pageSize);
      queryObject.setMaxResults(pageSize);
      return queryObject.getResultList();
    }

  private StringBuffer frameAddressQuery(String categoryIds, AddressDao address) {

    var city = address.getCityTownVillage();
    var district = address.getDistrict();
    var state = address.getState();

    StringBuffer sb = new StringBuffer(String.format(BASIC_QUERY, categoryIds));
    sb.append(" AND");
    sb.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_STATE, state));

    StringBuffer cityAndDistrictQuery = new StringBuffer("");
    if (!StringUtils.isEmpty(city)) {
      cityAndDistrictQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_CITY, city));
    }

    if (!StringUtils.isEmpty(district)) {
      if(!cityAndDistrictQuery.toString().isEmpty()) {
        cityAndDistrictQuery.append(" OR");
      }
      cityAndDistrictQuery.append(String.format(COMMON_WHERE, SEND_TO_TYPE_LOCATION_DIST, district));
    }

    if(!cityAndDistrictQuery.toString().isEmpty()) {
      sb.append(" OR ( "+cityAndDistrictQuery.toString()+" )");
    }

    return sb;
  }
  
  
  
  public PageWiseSearchResponse<ExperienceDto> getAllPaged(Integer pageNumber, Integer pageSize) {

      PageWiseSearchResponse<ExperienceDto> response = new PageWiseSearchResponse<>();
      PageRequest request = PageRequest.of(pageNumber-1 , pageSize);
      Page<ExperienceViewDao> page = experienceViewRepositoryJpa.findByDeletedTimeIsNull(request);

      List<ExperienceDto> experienceDtos = page.stream().parallel().map(dao -> dao.toDto())
              .collect(Collectors.toList());
      response.setPageCount(page.getTotalPages());
      response.setList(experienceDtos);

      return response;

  }
  
}
