package com.vire.repository;

import com.vire.dao.ChannelProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelProfileRepositoryJpa
    extends JpaRepository<ChannelProfileDao, Long>, JpaSpecificationExecutor<ChannelProfileDao> {
    Optional<ChannelProfileDao> findByChannelIdAndProfileId(Long communityId, Long profileId);
    Optional<ChannelProfileDao> deleteByChannelId(Long channelId);

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedChannels();
}
