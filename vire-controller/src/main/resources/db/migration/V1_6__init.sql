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

ALTER TABLE profile ADD COLUMN IS_BLOCKED  TINYINT(1) NULL DEFAULT 0 AFTER weightage;
ALTER TABLE t_social_post_chat ADD COLUMN is_creator_message TINYINT NULL DEFAULT 0 AFTER social_id, CHANGE COLUMN chat_initiator_profile_id social_creator_profile_id BIGINT NOT NULL ;


