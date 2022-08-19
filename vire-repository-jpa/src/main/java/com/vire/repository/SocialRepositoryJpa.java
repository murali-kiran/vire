package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dto.SocialDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialRepositoryJpa extends JpaRepository<SocialDao, Long> , JpaSpecificationExecutor<SocialDao> {
    List<SocialDao> findByCategoryId(Long categoryId);
    List<SocialDao> getBySendTo(final String searchString);
    List<SocialDao> findAll(Sort sort);
    @Query(value="SELECT s.* FROM t_social s JOIN t_social_post_send_to st ON st.social_id = s.social_id WHERE st.type='Community' and st.value = :communityId ORDER BY s.updated_time DESC", nativeQuery = true)
    List<SocialDao> findByCommunity(Long communityId);

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedSocialPosts();
}
