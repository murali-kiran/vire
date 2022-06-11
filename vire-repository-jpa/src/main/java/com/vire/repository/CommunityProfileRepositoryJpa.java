package com.vire.repository;

import com.vire.dao.CommunityProfileDao;
import com.vire.dao.SocialDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityProfileRepositoryJpa
    extends JpaRepository<CommunityProfileDao, Long>, JpaSpecificationExecutor<CommunityProfileDao> {
    List<CommunityProfileDao> deleteByCommunityId(Long communityId);
}
