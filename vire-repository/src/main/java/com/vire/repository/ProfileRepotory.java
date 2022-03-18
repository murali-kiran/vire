package com.vire.repository;

import com.vire.dao.ProfileDao;
import com.vire.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileRepotory {

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;

    public ProfileDto createProfile(final ProfileDto profileDto) {
        return profileRepositoryJpa.save(ProfileDao.fromDto(profileDto)).toDto();
    }

    public ProfileDto updateProfile(final ProfileDto profileDto) {
        var existingObject = profileRepositoryJpa.findById(profileDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return profileRepositoryJpa.save(ProfileDao.fromDto(profileDto)).toDto();
    }

    public List<ProfileDto> getAllProfiles() {

        return profileRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
}
