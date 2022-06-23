package com.vire.repository;

import com.vire.dao.CommunityDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepositoryJpa
    extends JpaRepository<CommunityDao, Long>, JpaSpecificationExecutor<CommunityDao> {
    List<CommunityDao> findBycommunityIdIn(List<Long> communityIDs);
    List<CommunityDao> findAll(Sort sort);
}
