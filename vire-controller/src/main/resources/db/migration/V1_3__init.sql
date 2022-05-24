CREATE TABLE master_details (
    master_details_id BIGINT NOT NULL,
    master_id BIGINT NOT NULL,
    detail_type VARCHAR(191) NOT NULL,
    detail_value VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (master_details_id)
);

CREATE TABLE profile_thumbsdown (
    profile_thumbsdown_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    thumbs_up_by BIGINT NOT NULL,
    thumbs_up_reason VARCHAR(30) NOT NULL,
    thumbs_up_description VARCHAR(30) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (profile_thumbsdown_id)
);

CREATE TABLE profile_thumbsup (
    profile_thumbsup_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    thumbs_up_by BIGINT NOT NULL,
    thumbs_up_reason VARCHAR(30) NOT NULL,
    thumbs_up_description VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (profile_thumbsup_id)
);

CREATE TABLE Feedback (
    feedback_id BIGINT NOT NULL,
    feedback_provider_id BIGINT NOT NULL,
    rating TINYINT NOT NULL,
    description VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feedback_id)
);

CREATE TABLE experience_report (
    experience_report_id BIGINT NOT NULL,
    experience_id BIGINT NOT NULL,
    reporter_id BIGINT NOT NULL,
    report_reason VARCHAR(30) NOT NULL,
    reportDescription VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (experience_report_id)
);

CREATE TABLE channel (
    channel_id BIGINT NOT NULL,
    name VARCHAR(191) NOT NULL,
    description VARCHAR(20) NOT NULL,
    creator_profile_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    rules VARCHAR(20) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (channel_id)
);

CREATE TABLE profile_followers (
    profile_followers_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    follower_id BIGINT NOT NULL,
    is_friend TINYINT(1) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (profile_followers_id)
);

CREATE TABLE Feed_report (
    feed_report_id BIGINT NOT NULL,
    feed_id BIGINT NOT NULL,
    reporter_id BIGINT NOT NULL,
    report_reason VARCHAR(30) NOT NULL,
    reportDescription VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feed_report_id)
);

CREATE TABLE channel_profile (
    channel_profile_id BIGINT NOT NULL,
    channel_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (channel_profile_id)
);

CREATE TABLE social_report (
    social_report_id BIGINT NOT NULL,
    social_id BIGINT NOT NULL,
    reporter_id BIGINT NOT NULL,
    report_reason VARCHAR(30) NOT NULL,
    reportDescription VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (social_report_id)
);


ALTER TABLE channel_profile ADD CONSTRAINT fk_channel_profile_profile_id FOREIGN KEY (channel_id) REFERENCES channel(channel_id);
