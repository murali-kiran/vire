package com.vire.repository;

import com.vire.dao.ProfileFollowersDao;
import com.vire.dto.ProfileFollowersDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileFollowersRepository {

  @Autowired
  ProfileFollowersRepositoryJpa profileFollowersRepositoryJpa;

  public ProfileFollowersDto create(final ProfileFollowersDto profileFollowersDto) {

    var profileFollowersDao = ProfileFollowersDao.fromDto(profileFollowersDto);
    profileFollowersDao.onPrePersist();

    return profileFollowersRepositoryJpa.save(profileFollowersDao).toDto();
  }

  public ProfileFollowersDto update(final ProfileFollowersDto profileFollowersDto) {

    var existingObject = profileFollowersRepositoryJpa.findById(profileFollowersDto.getProfileFollowersId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileFollowersDao = ProfileFollowersDao.fromDto(profileFollowersDto);
    profileFollowersDao.onPreUpdate();

    return profileFollowersRepositoryJpa.save(profileFollowersDao).toDto();
  }

  public ProfileFollowersDto updateFollowStatus(final ProfileFollowersDto profileFollowersDto) {

    var existingObject = profileFollowersRepositoryJpa.findByProfileIdAndFollowerId(profileFollowersDto.getProfileId(), profileFollowersDto.getFollowerId());

    if(existingObject == null) {
      throw new RuntimeException("Object not exists in db to update follow status ");
    }
    existingObject.onPreUpdate();
    existingObject.setStatus(profileFollowersDto.getStatus());
    return profileFollowersRepositoryJpa.save(existingObject).toDto();
  }
  public Optional<ProfileFollowersDto> delete(final Long profileFollowersId) {

    var optionalSocial = retrieveById(profileFollowersId);

    if (optionalSocial.isPresent()) {
      profileFollowersRepositoryJpa.deleteById(profileFollowersId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileFollowersDto> getAll() {

    return profileFollowersRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileFollowersDto> retrieveById(Long profileFollowersId) {

    return profileFollowersRepositoryJpa.findById(profileFollowersId).map(dao -> dao.toDto());
  }

  public List<ProfileFollowersDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileFollowersDao>(searchString).resolve();

    return profileFollowersRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

    public long getFriendCountOfProfile(Long profileId,Boolean isFriend) {
      return profileFollowersRepositoryJpa.findFollowersByProfileId(profileId).size();
    }


  /*public void getFriendsOfProfile(Long profileId, boolean b) {
  }*/
}
