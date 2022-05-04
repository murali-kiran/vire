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

}
