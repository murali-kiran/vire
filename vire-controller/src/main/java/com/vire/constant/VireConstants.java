package com.vire.constant;

import java.util.function.BooleanSupplier;

public class VireConstants {
    public final static String PERSONAL_PROFILE_REQUEST_PATH_API = "/api/v1/personalProfile";
    public final static String FIRM_PROFILE_REQUEST_PATH_API = "/api/v1/firmProfile";
    public final static String LOGIN_REQUEST_API = "/api/v1/login";
    public final static String SOCIAL_REQUEST_PATH_API = "/api/v1/social";
    public final static String CHAT_REQUEST_PATH_API = SOCIAL_REQUEST_PATH_API+"/chat";
    public final static String FILE_REQUEST_PATH_API = "/file";
    public final static String SENT_REQUEST_PATH_API = SOCIAL_REQUEST_PATH_API+"/sent";
    public final static String LIKE_REQUEST_PATH_API = SOCIAL_REQUEST_PATH_API+"/like";
    public final static String REPLY_REQUEST_PATH_API = SOCIAL_REQUEST_PATH_API+"/reply";
    public final static String COMMENT_REQUEST_PATH_API = SOCIAL_REQUEST_PATH_API+"/comment";
    public final static String FEEDS_REQUEST_PATH_API = "/api/v1/feeds";
    public final static String SEND_TO_REQUEST_PATH_API = FEEDS_REQUEST_PATH_API + "/sendto";
    public final static Boolean IS_AMAZON_SERVER = false;

    public final static String COMMUNITY_REQUEST_PATH_API = "/api/community";
    public final static String COMMUNITYPROFILE_REQUEST_PATH_API = "/api/communityprofile";
    public final static String FEEDCOMMENT_REQUEST_PATH_API = "/api/feedcomment";
    public final static String FEEDCOMMENTREPLY_REQUEST_PATH_API = "/api/feedcommentreply";
    public final static String FEEDLIKES_REQUEST_PATH_API = "/api/feedlikes";
    public final static String MASTER_REQUEST_PATH_API = "/api/master";
    public final static String EXPERIENCE_REQUEST_PATH_API = "/api/experience";
    public final static String EXPERIENCECOMMENT_REQUEST_PATH_API = "/api/experiencecomment";
    public final static String EXPERIENCECOMMENTREPLY_REQUEST_PATH_API = "/api/experiencecommentreply";
    public final static String EXPERIENCELIKES_REQUEST_PATH_API = "/api/experiencelikes";
    public final static String PROFILE_PATH_API = "api/profile";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_HEADER_STRING = "Authorization";

    public final static String MASTERDETAILS_REQUEST_PATH_API = "/api/masterdetails";
    public final static String PROFILEFOLLOWERS_REQUEST_PATH_API = "/api/profilefollowers";
    public final static String CHANNEL_REQUEST_PATH_API = "/api/channel";
    public final static String CHANNELPROFILE_REQUEST_PATH_API = "/api/channelprofile";
    public final static String PROFILETHUMBSUP_REQUEST_PATH_API = "/api/profilethumbsup";
    public final static String PROFILETHUMBSDOWN_REQUEST_PATH_API = "/api/profilethumbsdown";
    public final static String SOCIALREPORT_REQUEST_PATH_API = "/api/socialreport";
    public final static String FEEDREPORT_REQUEST_PATH_API = "/api/feedreport";
    public final static String EXPERIENCEREPORT_REQUEST_PATH_API = "/api/experiencereport";
    public final static String FEEDBACK_REQUEST_PATH_API = "/api/feedback";

    public final static String SOCIALCATEGORYMASTER_REQUEST_PATH_API = "/api/socialcategorymaster";
    public final static String PROFILESETTING_REQUEST_PATH_API = "/api/profilesetting";
    public final static String SOCIALCALLREQUEST_REQUEST_PATH_API = "/api/socialcallrequest";

    public final static String LOCATIONMASTER_REQUEST_PATH_API = "/api/locationmaster";
    public final static String ADMIN_PORTAL_API = "/admin";

    public final static String NOTIFICATION_REQUEST_PATH_API = "/api/notification";
}