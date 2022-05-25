package com.vire.repository;

import com.vire.dao.ProfileThumbsUpDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileThumbsUpRepositoryJpa
    extends JpaRepository<ProfileThumbsUpDao, Long>, JpaSpecificationExecutor<ProfileThumbsUpDao> {

    long countByProfileId(Long profileId);
}
