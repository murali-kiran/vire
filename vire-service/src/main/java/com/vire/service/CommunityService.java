package com.vire.service;

import com.vire.model.request.CommunityRequest;
import com.vire.model.response.CommunityResponse;
import com.vire.repository.CommunityRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public CommunityResponse create(final CommunityRequest request) {

        var dto = request.toDto(snowflake);

        return CommunityResponse.fromDto(communityRepository.create(dto));
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

    private CommunityResponse profileLoader(CommunityResponse response) {

        if (response.getCreatorProfile() != null
                && response.getCreatorProfile().getProfileId() != null) {
            response.getCreatorProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(response.getCreatorProfile().getProfileId())));
        }

        var communityProfileList = communityProfileService.retrieveByCommunityId(response.getCommunityId());
        if (communityProfileList != null) {
            for (var communityProfile : communityProfileList) {
                if (communityProfile.getProfile() != null) {
                    communityProfile.getProfile().cloneProperties(
                            profileService.retrieveProfileDtoById(
                                    Long.valueOf(communityProfile.getProfile().getProfileId())));
                }
            }
            response.setCommunityProfiles(communityProfileList);
        }
        return response;
    }
}
