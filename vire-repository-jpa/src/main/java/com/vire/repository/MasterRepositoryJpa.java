package com.vire.repository;

import com.vire.dao.MasterDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterRepositoryJpa
    extends JpaRepository<MasterDao, Long>, JpaSpecificationExecutor<MasterDao> {

    public List<MasterDao> findByMasterType(String masterType);
}
