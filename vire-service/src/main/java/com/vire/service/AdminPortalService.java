package com.vire.service;

import com.vire.model.response.*;
import com.vire.model.response.view.SocialViewResponse;
import com.vire.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import com.vire.dto.ProfileDto;
import com.vire.model.response.view.FeedsViewResponse;
import com.vire.repository.view.FeedsViewRepositoryJpa;

@Service
public class AdminPortalService {

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;

    @Autowired
    FeedsViewRepositoryJpa feedsViewRepositoryJpa;

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
    ChannelRepositoryJpa channelRepositoryJpa;

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

    @Autowired
    SocialService socialService;

    @Autowired ExperienceService experienceService;

    @Autowired FeedsService feedsService;

    @Autowired ProfileService profileService;

    public AdminHomeResponse getHomePageDetails() {
        AdminHomeResponse adminHomeResponse = new AdminHomeResponse();
        adminHomeResponse.setTotalUsers(profileRepositoryJpa.count());
        adminHomeResponse.setNewUsersToday(profileRepositoryJpa.getTodayCreatedProfiles());

        adminHomeResponse.setTotalFirmAccounts(firmProfileRepositoryJpa.count());
        adminHomeResponse.setNewFirmAccountsToday(firmProfileRepositoryJpa.getTodayCreatedFirmAccounts());

        adminHomeResponse.setTotalCommunities(communityRepositoryJpa.count());
        adminHomeResponse.setNewCommunitiesToday(communityRepositoryJpa.getTodayCreatedCommunityPosts());

        adminHomeResponse.setTotalChannels(channelRepositoryJpa.count());
        adminHomeResponse.setNewChannelsToday(channelRepositoryJpa.getTodayCreatedChannels());

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
        var feedsViewResponseList = feedsRepository.getAllFeedsViewDtos().stream().map(dto->FeedsViewResponse.fromDto(dto)).collect(Collectors.toList());

        for(FeedsViewResponse feedsViewResponse : feedsViewResponseList){
            feedsViewResponse.setMinimalProfileResponse(profileService.retrieveProfileDtoById(Long.valueOf(feedsViewResponse.getProfileId())));
        }

        return feedsViewResponseList;
    }

    public PageWiseSearchResponse<SocialPostResponse> getAllSocialsPaged(int pageNumber, int pageSize) {
        return socialService.getAllPaged(pageNumber,pageSize);
    }

    public PageWiseSearchResponse<ExperienceDetailResponse> getAllExperiencesPaged(Integer pageNumber, Integer pageSize) {
        return experienceService.getAllPaged(pageNumber,pageSize);
    }

    public FeedsResponse deleteFeed(String feedId) {
        return feedsService.deleteFeedsPost(Long.valueOf(feedId));
    }

    public SocialResponse deleteSocial(String socialId) {
        return socialService.deleteSocialPost(Long.valueOf(socialId));
    }

    public ExperienceResponse deleteExperience(String experienceId) {
        return  experienceService.delete(Long.valueOf(experienceId));
    }


}
