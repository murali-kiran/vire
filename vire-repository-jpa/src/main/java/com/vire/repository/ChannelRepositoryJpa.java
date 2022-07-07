package com.vire.repository;

import com.vire.dao.ChannelDao;
import com.vire.dao.CommunityDao;
import com.vire.dto.ChannelDto;
import com.vire.dto.CommunityDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ChannelRepositoryJpa
    extends JpaRepository<ChannelDao, Long>, JpaSpecificationExecutor<ChannelDao> {
    List<ChannelDao> findByChannelIdIn(List<Long> channelIDs, Sort sort);
    List<ChannelDao> findAll(Sort sort);
    @Query(value = "SELECT c.* FROM channel c JOIN channel_profile cp ON c.channel_id = cp.channel_id WHERE cp.profile_id = :profileId AND cp.status IN :statusList ORDER BY c.updated_time DESC", nativeQuery = true)
    List<ChannelDao> findChannelByProfileIdStatus(Long profileId, List<String> statusList);
}
