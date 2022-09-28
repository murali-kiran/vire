package com.vire.repository;

import com.vire.dao.ExperienceDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepositoryJpa
    extends JpaRepository<ExperienceDao, Long>, JpaSpecificationExecutor<ExperienceDao> {

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedExperiencePosts();

    @Query(value = "SELECT e.* FROM experience e WHERE e.category_id IN :categoryList ORDER BY e.updated_time DESC",nativeQuery = true)
    List<ExperienceDao> findByCategoryList(List<Long> categoryList);

    /*@Query(value="SELECT distinct s.feed_id FROM t_feeds s JOIN t_feeds_send_to st ON st.feed_id = s.feed_id WHERE st.type='Community' and st.value = :communityId ORDER BY s.updated_time DESC", nativeQuery = true)
    List<Long> findByCommunity(Long communityId);*/

}
