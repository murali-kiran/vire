package com.vire.repository;

import com.vire.dao.view.CommunityProfileFileViewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityProfileFileRepositoryJpa extends JpaRepository<CommunityProfileFileViewDao, Long> ,
        JpaSpecificationExecutor<CommunityProfileFileViewDao> {

    List<CommunityProfileFileViewDao> findByCommunityProfileId(Long communityProfileId);
}
