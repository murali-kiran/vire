package com.vire.repository;

import com.vire.dao.ProfileFollowersDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProfileFollowersRepositoryJpa
    extends JpaRepository<ProfileFollowersDao, Long>, JpaSpecificationExecutor<ProfileFollowersDao> {

    @Query(value="select distinct profile_id from profile_followers where status='Accepted' and  follower_id = :profileId " +
            "UNION " +
            " select distinct follower_id from profile_followers where status='Accepted' and profile_id = :profileId", nativeQuery = true)
    Set<Long> findFollowersByProfileId(Long profileId);
    ProfileFollowersDao findByProfileIdAndFollowerId(Long profileId, Long followerId);
}
