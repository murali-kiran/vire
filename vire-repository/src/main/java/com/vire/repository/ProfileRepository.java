package com.vire.repository;

import com.vire.dao.AddressDao;
import com.vire.dao.ProfileDao;
import com.vire.dto.ProfileDto;
import com.vire.repository.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileRepository {

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;
    @Autowired
    AddressRepositoryJpa addressRepositoryJpa;
    @Autowired
    FirmProfileRepositoryJpa firmProfileRepositoryJpa;
    @Autowired
    PersonalProfileRepositoryJpa personalProfileRepositoryJpa;


    @Transactional
    public ProfileDto createProfile(final ProfileDto profileDto) {

        var profileDao = ProfileDao.fromDto(profileDto);
        profileDao.onPrePersist();

        if (profileDto.getPersonalProfile() != null) {
            profileDao.getPersonalProfile().onPrePersist();
            var personalProfileDao = profileDao.getPersonalProfile();
            if (!CollectionUtils.isEmpty(personalProfileDao.getInterests())) {
                for (var interest : personalProfileDao.getInterests()) {
                    interest.onPrePersist();
                    interest.setPersonalProfile(personalProfileDao);
                }
            }

            //profileDao.getPersonalProfile().getPermanentAddress().onPrePersist();
            var permanentAddressDao = profileDao.getPersonalProfile().getPermanentAddress();//AddressDao.fromDto(profileDto.getPersonalProfile().getPermanentAddress());
            permanentAddressDao.onPrePersist();
            addressRepositoryJpa.save(permanentAddressDao);

            //profileDao.getPersonalProfile().getPresentAddress().onPrePersist();
            var presentAddressDao = profileDao.getPersonalProfile().getPresentAddress();
            presentAddressDao.onPrePersist();
            addressRepositoryJpa.save(presentAddressDao);
        } else {
            profileDao.getFirmProfile().onPrePersist();

            var addressDao = profileDao.getFirmProfile().getAddress();
            addressDao.onPrePersist();
            addressRepositoryJpa.save(addressDao);
        }

        return profileRepositoryJpa.save(profileDao).toDto();
    }

    @Transactional
    public ProfileDto updateProfile(final ProfileDto profileDto) {

        var optionalProfile = retrieveProfileById(profileDto.getProfileId());

        if (optionalProfile.isPresent()) {
            ProfileDao profileDao = ProfileDao.fromDto(profileDto);
            profileDao.onPreUpdate();

            if (profileDto.getPersonalProfile() != null) {
                profileDao.getPersonalProfile().onPreUpdate();

                var personalProfileDao = profileDao.getPersonalProfile();
                if (!CollectionUtils.isEmpty(personalProfileDao.getInterests())) {
                    for (var interest : personalProfileDao.getInterests()) {
                        interest.onPreUpdate();
                        interest.setPersonalProfile(personalProfileDao);
                    }
                }

                AddressDao permanentAddressDao = AddressDao.fromDto(profileDto.getPersonalProfile().getPermanentAddress());
                permanentAddressDao.onPreUpdate();
                addressRepositoryJpa.save(permanentAddressDao);

                AddressDao presentAddressDao = AddressDao.fromDto(profileDto.getPersonalProfile().getPresentAddress());
                presentAddressDao.onPreUpdate();
                addressRepositoryJpa.save(presentAddressDao);
            } else {
                profileDao.getFirmProfile().onPreUpdate();

                AddressDao addressDao = AddressDao.fromDto(profileDto.getFirmProfile().getAddress());
                addressDao.onPreUpdate();
                //addressRepositoryJpa.save(addressDao);
            }

            return profileRepositoryJpa.save(profileDao).toDto();
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

    public boolean isPersonalProfileExists(final Long profileId){
        return personalProfileRepositoryJpa.existsById(profileId);
    }

    public boolean isFirmProfileExists(final Long profileId){
        return firmProfileRepositoryJpa.existsById(profileId);
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
