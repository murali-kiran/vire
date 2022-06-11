package com.vire.repository;

import com.vire.dao.CommunityProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityProfileRepositoryJpa
    extends JpaRepository<CommunityProfileDao, Long>, JpaSpecificationExecutor<CommunityProfileDao> {

    Optional<CommunityProfileDao> findByCommunityIdAndProfileId(Long communityId, Long profileId);
    List<CommunityProfileDao> deleteByCommunityId(Long communityId);
}
