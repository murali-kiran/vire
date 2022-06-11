package com.vire.repository;

import com.vire.dao.CommunityProfileDao;
import com.vire.dao.SocialDao;
import com.vire.dto.CommunityProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityProfileRepositoryJpa
    extends JpaRepository<CommunityProfileDao, Long>, JpaSpecificationExecutor<CommunityProfileDao> {
    void deleteByCommunityId(Long communityId);

    Optional<CommunityProfileDao> findByCommunityIdAndProfileId(Long communityId, Long profileId);
}
