package com.vire.repository;

import com.vire.dao.AppRestrictionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRestrictionRepositoryJpa
    extends JpaRepository<AppRestrictionDao, Long>, JpaSpecificationExecutor<AppRestrictionDao> {}
