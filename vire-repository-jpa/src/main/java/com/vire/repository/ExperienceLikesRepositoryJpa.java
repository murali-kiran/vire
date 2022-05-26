package com.vire.repository;

import com.vire.dao.ExperienceLikesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceLikesRepositoryJpa
    extends JpaRepository<ExperienceLikesDao, Long>, JpaSpecificationExecutor<ExperienceLikesDao> {

    long countByLikerProfileId(Long id);
}
