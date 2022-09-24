package com.vire.service;

import com.vire.model.response.AdminHomeResponse;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import com.vire.dto.ProfileDto;
import com.vire.model.response.PageWiseSearchResponse;
import com.vire.model.response.view.FeedsViewResponse;

@Service
public class AdminPortalService {

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    FirmProfileRepositoryJpa firmProfileRepositoryJpa;

    @Autowired
    ProfileThumbsUpService profileThumbsUpService;

    @Autowired
    ProfileThumbsDownService profileThumbsDownService;

    @Autowired
    CommunityRepositoryJpa communityRepositoryJpa;

    @Autowired
    ChannelProfileRepositoryJpa channelProfileRepositoryJpa;

    @Autowired
    SocialRepositoryJpa socialRepositoryJpa;

    @Autowired
    FeedsRepositoryJpa feedsRepositoryJpa;

    @Autowired
    ExperienceRepositoryJpa experienceRepositoryJpa;

    @Autowired
    FeedReportRepositoryJpa feedReportRepositoryJpa;

    @Autowired
    ExperienceReportRepositoryJpa experienceReportRepositoryJpa;

    @Autowired
    SocialReportRepositoryJpa socialReportRepositoryJpa;
    
    @Autowired
    FeedsRepository feedsRepository;

    public AdminHomeResponse getHomePageDetails() {
        AdminHomeResponse adminHomeResponse = new AdminHomeResponse();
        adminHomeResponse.setTotalUsers(profileRepositoryJpa.count());
        adminHomeResponse.setNewUsersToday(profileRepositoryJpa.getTodayCreatedProfiles());

        adminHomeResponse.setTotalFirmAccounts(firmProfileRepositoryJpa.count());
        adminHomeResponse.setNewFirmAccountsToday(firmProfileRepositoryJpa.getTodayCreatedFirmAccounts());

        adminHomeResponse.setTotalCommunities(communityRepositoryJpa.count());
        adminHomeResponse.setNewCommunitiesToday(communityRepositoryJpa.getTodayCreatedCommunityPosts());

        adminHomeResponse.setTotalChannels(channelProfileRepositoryJpa.count());
        adminHomeResponse.setNewChannelsToday(channelProfileRepositoryJpa.getTodayCreatedChannels());

        adminHomeResponse.setAllFeedPosts(feedsRepositoryJpa.count());
        adminHomeResponse.setFeedPostsToday(feedsRepositoryJpa.getTodayCreatedFeedPosts());

        adminHomeResponse.setAllSocialPosts(socialRepositoryJpa.count());
        adminHomeResponse.setSocialPostsToday(socialRepositoryJpa.getTodayCreatedSocialPosts());

        adminHomeResponse.setAllExperiencePosts(experienceRepositoryJpa.count());
        adminHomeResponse.setExperiencePostsToday(experienceRepositoryJpa.getTodayCreatedExperiencePosts());

        adminHomeResponse.setAllCommunityPosts(communityRepositoryJpa.count());
        adminHomeResponse.setCommunityPostsToday(communityRepositoryJpa.getTodayCreatedCommunityPosts());

        adminHomeResponse.setAllReports(feedReportRepositoryJpa.count()
                +experienceReportRepositoryJpa.count()+socialReportRepositoryJpa.count());

        return adminHomeResponse;
    }

    public PageWiseSearchResponse<ProfileResponse> getAllUsers(Integer pageNumber, Integer pageSize) {

        PageWiseSearchResponse<ProfileDto> searchResponse = profileRepository.retrieveAllProfilesPaged(pageNumber,pageSize);
        List<ProfileDto> profileDtos = searchResponse.getList();

        List<ProfileResponse> profileResponses = profileDtos.stream()
                .map(dto -> ProfileResponse.fromDto(dto))
                .collect(Collectors.toList());


        profileResponses.forEach(profile -> {
            profile.setThumbsUpCount(Long.valueOf(profileThumbsUpService.search("profileId:"+profile.getProfileId()).size()));
            profile.setThumbsDownCount(Long.valueOf(profileThumbsDownService.search("profileId:"+profile.getProfileId()).size()));
        });

        PageWiseSearchResponse<ProfileResponse> response = new PageWiseSearchResponse<ProfileResponse>();
        response.setPageCount(searchResponse.getPageCount());
        response.setList(profileResponses);

        return response;
    }

    @Transactional
    public Optional<ProfileResponse> deleteProfile(final Long profileId) {
        if (profileRepository.isPersonalProfileExists(profileId) || profileRepository.isFirmProfileExists(profileId)) {
            return profileRepository.deleteProfile(profileId).map(dto -> ProfileResponse.fromDto(dto));
        } else {
            return Optional.empty();
        }

    }

    @Transactional
    public  Boolean blockProfile(final Long profileId, final boolean isBlock) {
        try{
            profileRepository.blockProfile(profileId, isBlock);
            return true;
        }catch (Exception ex){
            return  false;
        }
    }
    
    public List<FeedsViewResponse> getAllFeeds() {
        return  feedsRepository.getAllFeedsViewDtos().stream().map(dto->FeedsViewResponse.fromDto(dto)).collect(Collectors.toList());
    }

}
