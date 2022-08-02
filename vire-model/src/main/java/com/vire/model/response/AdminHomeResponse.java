package com.vire.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminHomeResponse {

    @JsonProperty("Total Users")
    private long totalUsers;
    @JsonProperty("Total firm accounts")
    private long totalFirmAccounts;
    @JsonProperty("Total Communities")
    private long totalCommunities;
    @JsonProperty("Total Channels")
    private long totalChannels;
    @JsonProperty("New users today")
    private long newUsersToday;
    @JsonProperty("New firm accounts today")
    private long newFirmAccountsToday;
    @JsonProperty("New Communities today")
    private long newCommunitiesToday;
    @JsonProperty("New Channels today")
    private long newChannelsToday;
    @JsonProperty("All Feeds")
    private long allFeedPosts;
    @JsonProperty("Today Feeds")
    private long feedPostsToday;
    @JsonProperty("All Social posts")
    private long allSocialPosts;
    @JsonProperty("All Experience")
    private long allExperiencePosts;
    @JsonProperty("All Communities")
    private long allCommunityPosts;
    @JsonProperty("All Channels")
    private long allChannelPosts;
    @JsonProperty("All Reports")
    private long allReports;
    @JsonProperty("Socials Posts Today")
    private long socialPostsToday;
    @JsonProperty("Experiences Today")
    private long experiencePostsToday;
    @JsonProperty("Communities Today")
    private long communityPostsToday;
    @JsonProperty("Channels Today")
    private long channelPostsToday;
    @JsonProperty("Reports Today")
    private long reportsPostsToday;
}
