package com.vire.service;

import com.vire.dto.CommunityProfileDto;
import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.request.CommunityRequest;
import com.vire.model.response.CommunityResponse;
import com.vire.model.response.MinimalProfileResponse;
import com.vire.repository.CommunityRepository;
import com.vire.utils.Snowflake;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    CommunityProfileService communityProfileService;

    @Autowired
    Snowflake snowflake;

    @Transactional
    public CommunityResponse create(final CommunityRequest request) {
        try{
        var communityDto = request.toDto(snowflake);
        communityDto =  communityRepository.create(communityDto);

        var communityProfileRequest = new CommunityProfileRequest();

        communityProfileRequest.setCommunityId(communityDto.getCommunityId().toString());
        communityProfileRequest.setProfileId(communityDto.getCreatorProfileId().toString());
        communityProfileRequest.setIsAdmin(true);
        communityProfileRequest.setStatus("Admin");
        communityProfileService.create(communityProfileRequest);

        return CommunityResponse.fromDto(communityDto);}
        catch(ConstraintViolationException e){
            throw new RuntimeException("Record already exists. Please update instead of create.");
        }
    }

    public CommunityResponse update(final CommunityRequest request) {

        var dto = request.toDto();

        return CommunityResponse.fromDto(communityRepository.update(dto));
    }

    public CommunityResponse delete(final Long communityId) {

        return communityRepository.delete(communityId)
                .map(dto -> profileLoader(CommunityResponse.fromDto(dto)))
                .get();
    }

    public List<CommunityResponse> getAll() {

        return communityRepository
                .getAll()
                .stream()
                .map(dto -> profileLoader(CommunityResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }
    public List<CommunityResponse> getAll(String profileId) {

        return communityRepository
                .getAll()
                .stream()
                .map(dto -> {
                    dto.setProfileId(profileId);
                    var response = profileLoader(CommunityResponse.fromDto(dto));
                    return response;
                })
                .collect(Collectors.toList());
    }

    public CommunityResponse retrieveById(Long communityId) {

        return communityRepository
                .retrieveById(communityId)
                .map(dto -> profileLoader(CommunityResponse.fromDto(dto)))
                .get();
    }

    public List<CommunityResponse> search(final String searchString) {

        return communityRepository
                .search(searchString)
                .stream()
                .map(dto -> profileLoader(CommunityResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }

    public List<MinimalProfileResponse> retrieveProfilesNotPartOfCommunity(final Long communityId){
        var communityProfiles = communityProfileService.retrieveByCommunityId(communityId+"");
        var profileIDs = communityProfiles.stream().map(communityProfile->Long.valueOf(communityProfile.getProfile().getProfileId())).collect(Collectors.toList());
        return profileService.retrieveProfilesNotInGivenIds(profileIDs);
    }

    private CommunityResponse profileLoader(CommunityResponse response) {

        if (response.getCreatorProfile() != null
                && response.getCreatorProfile().getProfileId() != null) {
            response.getCreatorProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(response.getCreatorProfile().getProfileId())));
        }
        String status = "NA";
        String loginProfileId = response.getLoginProfileId();
        if(loginProfileId != null) {
            status = "Join Community";
        }
        //var communityProfile = communityProfileService.search("( profileId:"+loginProfileId+" ) AND ( communityId:"+)
        var communityProfileList = communityProfileService.retrieveByCommunityId(response.getCommunityId());
        if (communityProfileList != null) {
            int i = 1;
            for (var communityProfile : communityProfileList) {
                if (communityProfile.getProfile() != null) {
                    communityProfile.getProfile().cloneProperties(
                            profileService.retrieveProfileDtoById(
                                    Long.valueOf(communityProfile.getProfile().getProfileId())));
                    if(loginProfileId != null && loginProfileId.equals(communityProfile.getProfile().getProfileId())) {
                        status = communityProfile.getStatus();
                        response.setCommunityProfileIdOfLoginUser(communityProfile.getCommunityProfileId());
                    }
                    if("Accepted".equals(communityProfile.getStatus())){
                        i++;
                    }
                }
            }
            response.setAcceptedUserCount(i);
            response.setCommunityProfiles(communityProfileList);
        }
        response.setProfileCommunityStatus(status);
        return response;
    }


    public List<CommunityResponse> retrieveCommunitiesJoined(Long profileId) {
        var profileJoinedCommunities = communityProfileService.retrieveByProfileId(profileId+"");
        var communityIDs = profileJoinedCommunities.stream().map(communityProfile->Long.valueOf(communityProfile.getCommunityId())).collect(Collectors.toList());
        return communityRepository
                .retrieveByIds(communityIDs)
                .stream()
                .map(dto -> profileLoader(CommunityResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }
}
