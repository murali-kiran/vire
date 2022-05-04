package com.vire.repository;

import com.vire.dao.CommunityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepositoryJpa
    extends JpaRepository<CommunityDao, Long>, JpaSpecificationExecutor<CommunityDao> {}
