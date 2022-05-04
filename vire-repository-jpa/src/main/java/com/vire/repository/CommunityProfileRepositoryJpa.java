package com.vire.repository;

import com.vire.dao.CommunityProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityProfileRepositoryJpa
    extends JpaRepository<CommunityProfileDao, Long>, JpaSpecificationExecutor<CommunityProfileDao> {}
