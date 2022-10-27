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
