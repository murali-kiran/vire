package com.vire.repository;

import com.vire.dao.ProfileThumbsDownDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileThumbsDownRepositoryJpa
    extends JpaRepository<ProfileThumbsDownDao, Long>, JpaSpecificationExecutor<ProfileThumbsDownDao> {

    long countByProfileId(Long profileId);
}
