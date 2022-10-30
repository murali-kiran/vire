package com.vire.repository.view;

import com.vire.dao.view.CommunityViewDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityViewRepositoryJpa
    extends JpaRepository<CommunityViewDao, Long>, JpaSpecificationExecutor<CommunityViewDao> {
    List<CommunityViewDao> findByCommunityIdIn(List<Long> communityIDs, Sort sort);
    List<CommunityViewDao> findAll(Sort sort);
}
