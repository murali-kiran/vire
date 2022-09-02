package com.vire.repository;

import com.vire.dao.ChannelDao;
import com.vire.dto.ChannelDto;
import com.vire.dto.ChannelDto;
import com.vire.dto.FeedsSendToDto;
import com.vire.repository.ChannelRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelRepository {

  @Autowired
  ChannelRepositoryJpa channelRepositoryJpa;
  @Autowired
  ChannelProfileRepositoryJpa channelProfileRepositoryJpa;
  @Autowired
  FeedsSendToRepository feedsSendToRepository;

  @Autowired
  FeedsRepositoryJpa feedsRepositoryJpa;
  @Autowired
  FeedsRepository feedsRepository;

  public ChannelDto create(final ChannelDto channelDto) {

    var channelDao = ChannelDao.fromDto(channelDto);
    channelDao.onPrePersist();

    return channelRepositoryJpa.save(channelDao).toDto();
  }

  public ChannelDto update(final ChannelDto channelDto) {

    var existingObject = channelRepositoryJpa.findById(channelDto.getChannelId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var channelDao = ChannelDao.fromDto(channelDto);
    channelDao.onPreUpdate();

    return channelRepositoryJpa.save(channelDao).toDto();
  }

  @Transactional
  public Optional<ChannelDto> delete(final Long channelId) {

    var optionalChannel = retrieveById(channelId);
    var feedsSendToDtos = feedsSendToRepository.searchSent("type:channel AND value:"+channelId);

    if (optionalChannel.isPresent()) {
      channelProfileRepositoryJpa.deleteByChannelId(channelId);
      channelRepositoryJpa.deleteById(channelId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    var communityAllFeeds=new ArrayList<Long>();
    for (FeedsSendToDto feedsSendToDto : feedsSendToDtos) {
      var communityFeed = feedsRepositoryJpa.findByCommunityAndFeedId(feedsSendToDto.getFeedId());
      if(communityFeed != null && communityFeed.isEmpty()) {
        communityAllFeeds.add(feedsSendToDto.getFeedId());
      }
      feedsSendToRepository.deleteSent(feedsSendToDto.getFeedsSendToId());
    }
    for(var feedId: communityAllFeeds) {
      feedsRepository.deleteFeedsPost(feedId);
    }
    return optionalChannel;
  }
  public List<ChannelDto> getAll() {

    return channelRepositoryJpa.findAll(Sort.by(Sort.Direction.DESC, "updatedTime"))
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ChannelDto> retrieveById(Long channelId) {

    return channelRepositoryJpa.findById(channelId).map(dao -> dao.toDto());
  }

  public List<ChannelDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ChannelDao>(searchString).resolve();

    return channelRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public List<ChannelDto> retrieveByIds(List<Long> channelIDs) {
    var channelDaos= channelRepositoryJpa.findByChannelIdIn(channelIDs, Sort.by(Sort.Direction.DESC, "updatedTime"));
    var channelDtos= channelDaos.stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
    return channelDtos;
  }
  public List<ChannelDto> retrieveByProfileIdStatus(Long profileId, List<String> statusList){
    return channelRepositoryJpa.findChannelByProfileIdStatus(profileId, statusList).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
