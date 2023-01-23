CREATE TABLE feed_notification (
    feed_notification_id BIGINT NOT NULL,
    feed_notification_type VARCHAR(191) NOT NULL,
    profile_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feed_notification_id)
);

CREATE TABLE profile_notification (
    profile_notification_id BIGINT NOT NULL,
    profile_notification_type VARCHAR(191) NOT NULL,
    profile_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (profile_notification_id)
);

CREATE TABLE social_notification (
    social_notification_id BIGINT NOT NULL,
    social_notification_type VARCHAR(191) NOT NULL,
    profile_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (social_notification_id)
);

CREATE TABLE community_notification (
    community_notification_id BIGINT NOT NULL,
    community_notification_type VARCHAR(191) NOT NULL,
    profile_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (community_notification_id)
);
DROP TABLE t_notification;
CREATE TABLE t_notification (
    t_notification_id BIGINT NOT NULL,
    notification_type VARCHAR(191) NOT NULL,
    notifier_profile_id BIGINT NOT NULL,
    is_read TINYINT(1) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (t_notification_id)
);


ALTER TABLE community_notification ADD CONSTRAINT fk_community_notification_notification_id FOREIGN KEY (community_notification_id) REFERENCES t_notification(t_notification_id);

ALTER TABLE social_notification ADD CONSTRAINT fk_social_notification_notification_id FOREIGN KEY (social_notification_id) REFERENCES t_notification(t_notification_id);

ALTER TABLE feed_notification ADD CONSTRAINT fk_feed_notification_notification_id FOREIGN KEY (feed_notification_id) REFERENCES t_notification(t_notification_id);

ALTER TABLE profile_notification ADD CONSTRAINT fk_profile_notification_notification_id FOREIGN KEY (profile_notification_id) REFERENCES t_notification(t_notification_id);

ALTER TABLE feed_notification ADD COLUMN feed_id BIGINT NOT NULL AFTER profile_id;
ALTER TABLE community_notification ADD COLUMN community_id BIGINT NOT NULL AFTER profile_id;
ALTER TABLE social_notification ADD COLUMN social_id BIGINT NOT NULL AFTER profile_id;

ALTER TABLE profile ADD COLUMN profile_status VARCHAR(45) NOT NULL DEFAULT 'active' AFTER profile_type;
ALTER TABLE t_social ADD COLUMN deleted_time BIGINT NULL DEFAULT NULL AFTER updated_time;
ALTER TABLE t_feeds ADD COLUMN deleted_time BIGINT NULL DEFAULT NULL AFTER updated_time;
ALTER TABLE experience ADD COLUMN deleted_time BIGINT NULL DEFAULT NULL AFTER updated_time;

ALTER TABLE t_notification ADD COLUMN deleted_time BIGINT NULL DEFAULT NULL AFTER updated_time, ADD COLUMN message VARCHAR(191) NULL AFTER is_read;

ALTER TABLE social_notification ADD COLUMN social_misc_info_id BIGINT NULL AFTER social_id,ADD COLUMN misc_type VARCHAR(191) NULL AFTER social_misc_info_id;

ALTER TABLE profile_followers ADD UNIQUE profile_followers_unique_index(profile_id, follower_id);
ALTER TABLE experience CHANGE COLUMN location_state location_state VARCHAR(45) NULL ;

CREATE TABLE requester_profile_setting (
    requester_profile_setting_id BIGINT NOT NULL,
    setting_type VARCHAR(255) NOT NULL,
    is_enabled TINYINT(1) NOT NULL,
    profile_id BIGINT NOT NULL,
    requester_profile_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (requester_profile_setting_id)
);

ALTER TABLE experience_report CHANGE COLUMN reportDescription report_description VARCHAR(191) NOT NULL ;
ALTER TABLE requester_profile_setting ADD UNIQUE unique_index_requester_profile_setting(profile_id, requester_profile_id, setting_type);

CREATE TABLE t_profile_report (
    t_profile_report_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    reporter_id BIGINT NOT NULL,
    report_reason VARCHAR(191) NOT NULL,
    report_description VARCHAR(500) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (t_profile_report_id)
);
CREATE TABLE t_app_restriction (
    t_app_restriction_id BIGINT NOT NULL,
    interests_selection_limit BIGINT NOT NULL,
    creating_communities_limit BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (t_app_restriction_id)
);
CREATE TABLE t_admin_message (
    t_admin_message_id BIGINT NOT NULL,
    message_type VARCHAR(100) NOT NULL,
    message VARCHAR(500) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (t_admin_message_id)
);