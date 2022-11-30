package com.vire.repository.view;

import com.vire.dao.ExperienceDao;
import com.vire.dao.view.ExperienceViewDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ExperienceViewRepositoryJpa
    extends JpaRepository<ExperienceViewDao, Long>, JpaSpecificationExecutor<ExperienceViewDao> {

    Page<ExperienceViewDao> findByDeletedTimeIsNull(Pageable pageable);

}
