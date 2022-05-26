package com.vire.repository;

import com.vire.dao.ProfileFollowersDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileFollowersRepositoryJpa
    extends JpaRepository<ProfileFollowersDao, Long>, JpaSpecificationExecutor<ProfileFollowersDao> {

    long countDistinctFollowerIdByProfileIdAndIsFriend(Long id,Boolean isFriend);
}
