package com.vire.repository;

import com.vire.dao.ProfileReportDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileReportRepositoryJpa
    extends JpaRepository<ProfileReportDao, Long>, JpaSpecificationExecutor<ProfileReportDao> {}
