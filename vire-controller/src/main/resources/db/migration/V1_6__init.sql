ALTER TABLE profile_thumbsdown CHANGE COLUMN thumbs_up_reason thumbs_up_reason VARCHAR(50) NOT NULL,CHANGE COLUMN thumbs_up_description thumbs_up_description VARCHAR(250) NOT NULL ;
ALTER TABLE profile_thumbsup CHANGE COLUMN thumbs_up_reason thumbs_up_reason VARCHAR(50) NOT NULL,CHANGE COLUMN thumbs_up_description thumbs_up_description VARCHAR(250) NOT NULL ;
CREATE TABLE t_notification (
    t_notification_id BIGINT NOT NULL,
    creator_profile_id BIGINT NOT NULL,
    responder_profile_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    post_type VARCHAR(20) NOT NULL,
    respond_reason VARCHAR(100) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (t_notification_id)
);

ALTER TABLE t_feeds ADD COLUMN send_to_followers TINYINT NULL DEFAULT 0 AFTER parent_feed_id;
