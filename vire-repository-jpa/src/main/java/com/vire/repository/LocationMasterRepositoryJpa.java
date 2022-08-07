package com.vire.repository;

import com.vire.dao.LocationMasterDao;
import com.vire.dto.LocationMasterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationMasterRepositoryJpa
    extends JpaRepository<LocationMasterDao, Long>, JpaSpecificationExecutor<LocationMasterDao> {

    @Query("SELECT DISTINCT district FROM LocationMasterDao WHERE state IN (:state)")
    List<String> retrieveDistrictsByState(@Param("state") String state);
    @Query("SELECT DISTINCT state FROM LocationMasterDao WHERE country IN (:country)")
    List<String> retrieveStatesByCountry(@Param("country") String country);
    @Query("SELECT DISTINCT city FROM LocationMasterDao WHERE state IN (:state) AND district IN (:district)")
    List<String> retrieveCitiesByStateAndDist(@Param("state") String state, @Param("district") String district);
    List<LocationMasterDao> findDistinctDistrictByState(String state);
}
