package com.vire.repository;

import com.vire.dao.FeedsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeedsRepositoryJpa extends JpaRepository<FeedsDao, Long> , JpaSpecificationExecutor<FeedsDao> {
    Integer countByParentFeedId(Long feedId);


    @Query(value="SELECT s.feed_id FROM t_feeds s JOIN t_feeds_send_to st ON st.feed_id = s.feed_id WHERE st.type='community' and st.value = :communityId ORDER BY s.updated_time DESC", nativeQuery = true)
    Set<Long> findByCommunity(Long communityId);

    @Query(value="SELECT s.feed_id FROM t_feeds s JOIN t_feeds_send_to st ON st.feed_id = s.feed_id WHERE st.type='channel' and st.value = :channelId ORDER BY s.updated_time DESC", nativeQuery = true)
    Set<Long> findByChannel(Long channelId);

    @Query(value="SELECT s.feed_id FROM t_feeds s JOIN t_feeds_send_to st ON st.feed_id = s.feed_id WHERE st.type='community' AND st.feed_id = :feedId", nativeQuery = true)
    Set<Long> findByCommunityAndFeedId(Long feedId);

    @Query(value = "select count(*) from t_feeds where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedFeedPosts();
}
