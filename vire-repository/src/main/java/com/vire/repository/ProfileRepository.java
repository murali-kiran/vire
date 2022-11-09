package com.vire.repository;

import com.vire.dao.AddressDao;
import com.vire.dao.ProfileDao;
import com.vire.dao.ProfileSettingDao;
import com.vire.dto.ExperienceDto;
import com.vire.dto.FeedsDto;
import com.vire.dao.view.ProfileViewDao;
import com.vire.dto.ProfileDto;
import com.vire.dto.SocialDto;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.repository.search.*;
import com.vire.repository.view.ProfileViewRepositoryJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileRepository {

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;

    @Autowired
    ProfileViewRepositoryJpa profileViewRepositoryJpa;

    @Autowired
    AddressRepositoryJpa addressRepositoryJpa;
    @Autowired
    FirmProfileRepositoryJpa firmProfileRepositoryJpa;
    @Autowired
    PersonalProfileRepositoryJpa personalProfileRepositoryJpa;
    @Autowired
    SocialRepository socialRepository;
    @Autowired
    FeedsRepository feedsRepository;
    @Autowired
    ExperienceRepository experienceRepository;

    @Transactional
    public ProfileDto createProfile(final ProfileDto profileDto) {

        var profileDao = ProfileDao.fromDto(profileDto);
        profileDao.onPrePersist();

        if (!CollectionUtils.isEmpty(profileDao.getProfileSettings())) {
            for (var profileSettingType : profileDao.getProfileSettings()) {
                profileSettingType.onPrePersist();
                profileSettingType.setProfile(profileDao);
            }
        }

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
            var permanentAddressDao = profileDao.getPersonalProfile().getPermanentAddress();
            if(permanentAddressDao!=null) {
                permanentAddressDao.onPrePersist();
                addressRepositoryJpa.save(permanentAddressDao);
            }

            //profileDao.getPersonalProfile().getPresentAddress().onPrePersist();
            var presentAddressDao = profileDao.getPersonalProfile().getPresentAddress();
            if(presentAddressDao!=null) {
                presentAddressDao.onPrePersist();
                addressRepositoryJpa.save(presentAddressDao);
            }
        } else {
            profileDao.getFirmProfile().onPrePersist();

            var addressDao = profileDao.getFirmProfile().getAddress();
            if(addressDao!=null) {
                addressDao.onPrePersist();
                addressRepositoryJpa.save(addressDao);
            }
        }

        return profileRepositoryJpa.save(profileDao).toDto();
    }

    @Transactional
    @CachePut(value="profileDto", key="#result.profileId")
    public ProfileDto updateProfile(final ProfileDto profileDto) {
        var optionalProfile = retrieveProfileById(profileDto.getProfileId());
        if (optionalProfile.isPresent()) {
            ProfileDao profileDao = ProfileDao.fromDto(profileDto);
            profileDao.setPassword(optionalProfile.get().getPassword());
            profileDao.setFirstLogin(optionalProfile.get().getFirstLogin());
            profileDao.setProfileType(optionalProfile.get().getProfileType());
            profileDao.setProfileStatus(optionalProfile.get().getProfileStatus());
            profileDao.onPreUpdate();
            if (!CollectionUtils.isEmpty(profileDto.getProfileSettings())) {
                profileDao.setProfileSettings(profileDto.getProfileSettings()
                        .stream()
                        .map(profileSettingDto -> {
                            var profileSettingDao = ProfileSettingDao.fromDto(profileSettingDto);
                            profileSettingDao.setProfile(profileDao);
                            profileSettingDao.onPreUpdate();
                            return profileSettingDao;
                        }).collect(Collectors.toList()));
            }
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
                addressRepositoryJpa.save(addressDao);
            }

            return profileRepositoryJpa.save(profileDao).toDto();
        } else {
            throw new RuntimeException("Profile Object not exists in DB");
        }
    }

    @Transactional
    public ProfileDto updateProfileSetting(final Long profileId, Boolean isPrivateAccount, Boolean showFriends) {
        var optionalProfile = profileRepositoryJpa.findById(profileId);
        if(!optionalProfile.isEmpty()){
            List<ProfileSettingDao> profileSettings = optionalProfile.get().getProfileSettings();
            for (ProfileSettingDao profileSettingDao:profileSettings) {
                if(profileSettingDao.getSettingType().equals("isPrivate")){
                    profileSettingDao.setIsEnable(isPrivateAccount);
                }else if(profileSettingDao.getSettingType().equals("showFriends")){
                    profileSettingDao.setIsEnable(showFriends);
                }
            }
            optionalProfile.get().setProfileSettings(profileSettings);
            return profileRepositoryJpa.save(optionalProfile.get()).toDto();
        }else{
            throw new RuntimeException("Profile Not found with ID:"+profileId);
        }
    }

    @Transactional
    public Optional<ProfileDto> deleteProfile(final Long profileId) {

        var optionalProfile = retrieveProfileById(profileId);
        if (optionalProfile.isPresent()) {
            //profileRepositoryJpa.deleteById(profileId);
            List<SocialDto> socialDtos = socialRepository.search("profileId:"+profileId);
            for (SocialDto socialDto : socialDtos) {
                socialRepository.deleteSocialPost(socialDto.getSocialId());
            }
            List<FeedsDto> feedsDtos = feedsRepository.search("profileId:"+profileId);
            for (FeedsDto feedDto : feedsDtos) {
                feedsRepository.deleteFeedsPost(feedDto.getFeedId());
            }
            List<ExperienceDto> experienceDtos = experienceRepository.search("profileId:"+profileId);
            for (ExperienceDto experienceDto : experienceDtos) {
                experienceRepository.delete(experienceDto.getExperienceId());
            }
            profileRepositoryJpa.updateProfileStatus(profileId, "deleted");
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

    public PageWiseSearchResponse<ProfileDto> retrieveAllProfilesPaged(Integer pageNumber, Integer pageSize) {

        PageWiseSearchResponse searchResponse = new PageWiseSearchResponse<ProfileDto>();
        PageRequest request = PageRequest.of(pageNumber-1 , pageSize);


        Page<ProfileViewDao> page = profileViewRepositoryJpa.findAll(request);
        searchResponse.setPageCount(page.getTotalPages());
        List<ProfileDto> profileDtos =  page.stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());

        searchResponse.setList(profileDtos);

        return searchResponse;
    }

    public PageWiseSearchResponse<ProfileDto> retrieveAllBlockedProfileViewsPaged(Integer pageNumber, Integer pageSize) {

        PageWiseSearchResponse searchResponse = new PageWiseSearchResponse<ProfileDto>();
        PageRequest request = PageRequest.of(pageNumber-1 , pageSize);


        Page<ProfileViewDao> page = profileViewRepositoryJpa.findByProfileStatus("blocked",request);
        searchResponse.setPageCount(page.getTotalPages());
        List<ProfileDto> profileDtos =  page.stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());

        searchResponse.setList(profileDtos);

        return searchResponse;
    }

    public boolean isPersonalProfileExists(final Long profileId){
        return personalProfileRepositoryJpa.existsById(profileId);
    }

    public boolean isFirmProfileExists(final Long profileId){
        return firmProfileRepositoryJpa.existsById(profileId);
    }

    @Cacheable(value="profileDto", key="#profileId")
    public ProfileDto retrieveProfileDtoById(final Long profileId) {

        if(retrieveProfileById(profileId) !=  null) {
            return retrieveProfileById(profileId).get();
        }
        else{
            throw new RuntimeException("No profile present with id:"+profileId);
        }
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

    public PageWiseSearchResponse searchProfilesPaged(final String searchString,Integer pageNumber,Integer pageSize) {

        PageWiseSearchResponse searchResponse = new PageWiseSearchResponse<ProfileDto>();
        PageRequest request = PageRequest.of(pageNumber-1 , pageSize);

        var spec = new CustomSpecificationResolver<ProfileViewDao>(searchString).resolve();

        Page<ProfileViewDao> page = profileViewRepositoryJpa.findAll(spec,request);
        searchResponse.setPageCount(page.getTotalPages());
        List<ProfileDto> profileDtos =  page.stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());

        searchResponse.setList(profileDtos);

        return searchResponse;
    }

    public  Optional<ProfileDao> loginWithEmail(final String email, final String password){
        return profileRepositoryJpa.findByEmailIdAndPasswordAndProfileStatus(email,password,"active");
    }

    public  Optional<ProfileDao> loginWithPhoneNumber(final String mobileNumber, final String password){
        return profileRepositoryJpa.findByMobileNumberAndPasswordAndProfileStatus(mobileNumber,password,"active");
    }

    public  Optional<ProfileDao> findByEmailId(final String email){
        return profileRepositoryJpa.findByEmailIdAndProfileStatus(email, "active");
    }

    public  Optional<ProfileDao> findByMobileNumber(final String mobileNumber){
        return profileRepositoryJpa.findByMobileNumberAndProfileStatus(mobileNumber, "active");
    }

    public  List<ProfileDao> findByEmailIdOrMobileNumber(final String email, final String mobileNumber){
        return profileRepositoryJpa.findByEmailIdOrMobileNumberAndProfileStatus(email, mobileNumber, "active");
    }

    public List<ProfileDto> retrieveProfilesNotInGivenIds(List<Long> profileIDs) {

        var profileDaos= profileRepositoryJpa.findByProfileIdNotIn(profileIDs);
        var profileDtos= profileDaos.stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
        return profileDtos;
    }

    public void updatePasswordViaEmailAndProfileId(Long profileId,String emailOrPassword, String password) {
        profileRepositoryJpa.updatePasswordViaEmailAndProfileId(profileId,emailOrPassword,password);
    }

    public void updatePasswordViaMobileNumberAndProfileId(Long profileId, String emailOrPassword, String password) {
        profileRepositoryJpa.updatePasswordViaPhoneNumberAndProfileId(profileId,emailOrPassword,password);
    }

    public void updateEmailViaOldEmailAndProfileId(Long profileId,String emailOrphonenumber, String newEmail) {
        profileRepositoryJpa.updateEmailViaOldEmailAndProfileId(profileId,emailOrphonenumber, newEmail);
    }

    public void updateEmailViaMobileNumberAndProfileId(Long profileId,String emailOrphonenumber, String newEmail) {
        profileRepositoryJpa.updateEmailViaMobileNumberAndProfileId(profileId,emailOrphonenumber, newEmail);
    }

    public void blockProfile(Long profileId,Boolean isBlock) {
        profileRepositoryJpa.updateProfileStatus(profileId, isBlock?"blocked":"active");
    }
}
