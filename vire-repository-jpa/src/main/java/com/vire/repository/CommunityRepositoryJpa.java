package com.vire.repository;

import com.vire.dao.CommunityDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepositoryJpa
    extends JpaRepository<CommunityDao, Long>, JpaSpecificationExecutor<CommunityDao> {
    List<CommunityDao> findByCommunityIdIn(List<Long> communityIDs, Sort sort);
    List<CommunityDao> findAll(Sort sort);
    @Query(value = "SELECT c.* FROM community c JOIN community_profile cp ON c.community_id = cp.community_id WHERE cp.profile_id = :profileId AND cp.status IN :statusList ORDER BY c.updated_time DESC", nativeQuery = true)
    List<CommunityDao> findCommunityByProfileIdStatus(Long profileId, List<String> statusList);

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedCommunityPosts();
    
    @Modifying
    @Query("update CommunityDao c set c.isBlocked = :isBlocked where c.communityId = :communityId")
    void blockProfile(@Param(value = "communityId") long communityId, @Param(value = "isBlocked") Boolean isBlocked);
}
