package com.vire.repository;

import com.vire.dao.ExperienceLikesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceLikesRepositoryJpa
    extends JpaRepository<ExperienceLikesDao, Long>, JpaSpecificationExecutor<ExperienceLikesDao> {

    long countByLikerProfileId(Long id);
    @Query(value="Select count(*) from experience e join experience_likes el on e.experience_id = el.experience_id where e.profile_id = :profileId", nativeQuery = true)
    Integer countByExperienceProfile(Long profileId);
}
