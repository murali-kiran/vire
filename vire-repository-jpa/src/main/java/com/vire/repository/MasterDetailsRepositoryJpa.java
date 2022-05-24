package com.vire.repository;

import com.vire.dao.MasterDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterDetailsRepositoryJpa
    extends JpaRepository<MasterDetailsDao, Long>, JpaSpecificationExecutor<MasterDetailsDao> {}
