package com.vire.repository;

import com.vire.dao.ChannelProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelProfileRepositoryJpa
    extends JpaRepository<ChannelProfileDao, Long>, JpaSpecificationExecutor<ChannelProfileDao> {
    Optional<ChannelProfileDao> findByChannelIdAndProfileId(Long communityId, Long profileId);
    Optional<ChannelProfileDao> deleteByChannelId(Long channelId);
    List<ChannelProfileDao> findAllByProfileId(Long profileId);

    @Query(value = "select count(*) from channel_profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedChannels();
}
