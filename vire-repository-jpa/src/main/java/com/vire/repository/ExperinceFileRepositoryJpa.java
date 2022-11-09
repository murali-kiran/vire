package com.vire.repository;

import com.vire.dao.view.ExperienceFileViewDao;
import com.vire.dao.view.ExperienceFileViewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperinceFileRepositoryJpa extends JpaRepository<ExperienceFileViewDao, Long> ,
        JpaSpecificationExecutor<ExperienceFileViewDao> {

    List<ExperienceFileViewDao> findByExperienceId(Long feedId);

}
