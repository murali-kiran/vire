package com.vire.repository;

import com.vire.dao.ExperienceDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepositoryJpa
    extends JpaRepository<ExperienceDao, Long>, JpaSpecificationExecutor<ExperienceDao> {

    @Query(value = "select count(*) from experience where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedExperiencePosts();

    @Query(value = "SELECT e.* FROM experience e WHERE e.category_id IN :categoryList ORDER BY e.updated_time DESC",nativeQuery = true)
    List<ExperienceDao> findByCategoryList(List<Long> categoryList);

    /*@Query(value="SELECT distinct s.feed_id FROM t_feeds s JOIN t_feeds_send_to st ON st.feed_id = s.feed_id WHERE st.type='Community' and st.value = :communityId ORDER BY s.updated_time DESC", nativeQuery = true)
    List<Long> findByCommunity(Long communityId);*/
    @Modifying
    @Query("update ExperienceDao e set e.deletedTime = :deletedTime where e.experienceId = :experienceId")
    void updateDeletedTime(@Param(value = "deletedTime") long deletedTime, @Param(value = "experienceId") long experienceId);
    //Page<ExperienceDao> findAllPaged(Sort sort, Pageable pageable);
}
