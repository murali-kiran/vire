package com.vire.repository;

import com.vire.dao.AddressDao;
import com.vire.dao.ProfileDao;
import com.vire.dto.ProfileDto;
import com.vire.repository.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileRepository {

  @Autowired ProfileRepositoryJpa profileRepositoryJpa;
  @Autowired AddressRepositoryJpa addressRepositoryJpa;

  @Transactional
  public ProfileDto createProfile(final ProfileDto profileDto) {
    AddressDao addressDao = null;

    if(profileDto.getPersonalProfile()!=null) {
      addressDao = AddressDao.fromDto(profileDto.getPersonalProfile().getPermanentAddress());
    } else {
      addressDao = AddressDao.fromDto(profileDto.getFirmProfile().getAddress());
    }

    addressRepositoryJpa.save(addressDao);
    ProfileDao profileDao = ProfileDao.fromDto(profileDto);
    return profileRepositoryJpa.save(ProfileDao.fromDto(profileDto)).toDto();
  }

  public ProfileDto updateProfile(final ProfileDto profileDto) {

    var optionalProfile = retrieveProfileById(profileDto.getProfileId());

    if (optionalProfile.isPresent()) {
      return profileRepositoryJpa.save(ProfileDao.fromDto(profileDto)).toDto();
    } else {
      throw new RuntimeException("Profile Object not exists in DB");
    }
  }

  public Optional<ProfileDto> deleteProfile(final Long profileId) {

    var optionalProfile = retrieveProfileById(profileId);
    if (optionalProfile.isPresent()) {
      profileRepositoryJpa.deleteById(profileId);
    } else {
      return Optional.empty();
    }
    return optionalProfile;
  }

  public List<ProfileDto> retrieveAllProfiles() {

    return profileRepositoryJpa.findAll().stream()
        .map(dao -> dao.toDto())
        .collect(Collectors.toList());
  }

  public Optional<ProfileDto> retrieveProfileById(final Long profileId) {

    return profileRepositoryJpa.findById(profileId).map(dao -> dao.toDto());
  }

  public List<ProfileDto> searchProfiles(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileDao>(searchString).resolve();

    return profileRepositoryJpa.findAll(spec).stream()
        .map(dao -> dao.toDto())
        .collect(Collectors.toList());
  }
}
