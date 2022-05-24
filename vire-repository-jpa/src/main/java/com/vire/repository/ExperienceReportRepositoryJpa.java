package com.vire.repository;

import com.vire.dao.ExperienceReportDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceReportRepositoryJpa
    extends JpaRepository<ExperienceReportDao, Long>, JpaSpecificationExecutor<ExperienceReportDao> {}
