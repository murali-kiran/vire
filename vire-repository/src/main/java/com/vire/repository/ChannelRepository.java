package com.vire.repository;

import com.vire.dao.ChannelDao;
import com.vire.dto.ChannelDto;
import com.vire.dto.CommunityDto;
import com.vire.repository.ChannelRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelRepository {

  @Autowired
  ChannelRepositoryJpa channelRepositoryJpa;

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

  public Optional<ChannelDto> delete(final Long channelId) {

    var optionalSocial = retrieveById(channelId);

    if (optionalSocial.isPresent()) {
      channelRepositoryJpa.deleteById(channelId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
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

    return channelRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public List<ChannelDto> retrieveByIds(List<Long> channelIDs) {
    var channelDaos= channelRepositoryJpa.findByChannelIdIn(channelIDs);
    var channelDtos= channelDaos.stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
    return channelDtos;
  }
}
