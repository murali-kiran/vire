package com.vire.repository;

import com.vire.dao.ChannelProfileDao;
import com.vire.dto.ChannelProfileDto;
import com.vire.dto.CommunityProfileDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelProfileRepository {

  @Autowired
  ChannelProfileRepositoryJpa channelProfileRepositoryJpa;

  public ChannelProfileDto create(final ChannelProfileDto channelProfileDto) {

    var channelProfileDao = ChannelProfileDao.fromDto(channelProfileDto);
    channelProfileDao.onPrePersist();

    return channelProfileRepositoryJpa.save(channelProfileDao).toDto();
  }

  public ChannelProfileDto update(final ChannelProfileDto channelProfileDto) {

    var existingObject = channelProfileRepositoryJpa.findById(channelProfileDto.getChannelProfileId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var channelProfileDao = ChannelProfileDao.fromDto(channelProfileDto);
    channelProfileDao.onPreUpdate();

    return channelProfileRepositoryJpa.save(channelProfileDao).toDto();
  }

  public Optional<ChannelProfileDto> delete(final Long channelProfileId) {

    var optionalSocial = retrieveById(channelProfileId);

    if (optionalSocial.isPresent()) {
      channelProfileRepositoryJpa.deleteById(channelProfileId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ChannelProfileDto> getAll() {

    return channelProfileRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ChannelProfileDto> retrieveById(Long channelProfileId) {

    return channelProfileRepositoryJpa.findById(channelProfileId).map(dao -> dao.toDto());
  }

  public List<ChannelProfileDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ChannelProfileDao>(searchString).resolve();

    return channelProfileRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ChannelProfileDto> retrieveByChannelIdAndProfileId(Long channelId, Long profileId) {
    return channelProfileRepositoryJpa.findByChannelIdAndProfileId(channelId,profileId).map(dao -> dao.toDto());
  }
}
