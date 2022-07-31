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
        adminHomeResponse.setNewUsersToday(profileRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setTotalFirmAccounts(firmProfileRepositoryJpa.count());
        adminHomeResponse.setNewFirmAccountsToday(firmProfileRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setTotalCommunities(communityRepositoryJpa.count());
        adminHomeResponse.setNewCommunitiesToday(communityRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setTotalChannels(channelProfileRepositoryJpa.count());
        adminHomeResponse.setNewChannelsToday(channelProfileRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setAllFeedPosts(feedsRepositoryJpa.count());
        adminHomeResponse.setFeedPostsToday(feedsRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setAllSocialPosts(socialRepositoryJpa.count());
        adminHomeResponse.setSocialPostsToday(socialRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setAllExperiencePosts(experienceRepositoryJpa.count());
        adminHomeResponse.setExperiencePostsToday(experienceRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setAllCommunityPosts(communityRepositoryJpa.count());
        adminHomeResponse.setCommunityPostsToday(communityRepositoryJpa.countByCreatedTime(Instant.now().toEpochMilli()));

        adminHomeResponse.setAllReports(feedReportRepositoryJpa.count()
                +experienceReportRepositoryJpa.count()+socialReportRepositoryJpa.count());

        return adminHomeResponse;
    }

}
