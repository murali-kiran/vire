package com.vire.model.response;

import lombok.Data;

@Data
public class AdminHomeResponse {


    private long totalUsers;

    private long totalFirmAccounts;

    private long totalCommunities;

    private long totalChannels;

    private long newUsersToday;

    private long newFirmAccountsToday;

    private long newCommunitiesToday;

    private long newChannelsToday;

    private long allFeedPosts;

    private long feedPostsToday;

    private long allSocialPosts;

    private long allExperiencePosts;

    private long allCommunityPosts;

    private long allChannelPosts;

    private long allReports;

    private long socialPostsToday;

    private long experiencePostsToday;

    private long communityPostsToday;

    private long channelPostsToday;

    private long reportsPostsToday;
}
