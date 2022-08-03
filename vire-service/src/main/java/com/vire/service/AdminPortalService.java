package com.vire.service;

import com.vire.model.response.AdminHomeResponse;
import com.vire.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AdminPortalService {

    @Autowired
    ProfileRepositoryJpa profileRepositoryJpa;

    @Autowired
    FirmProfileRepositoryJpa firmProfileRepositoryJpa;

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

}
