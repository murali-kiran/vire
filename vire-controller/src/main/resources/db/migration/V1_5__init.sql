
CREATE TABLE feed_files (
    feed_file_id BIGINT NOT NULL,
    feed_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feed_file_id)
);

CREATE TABLE experience_files (
    experience_file_id BIGINT NOT NULL,
    experience_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (experience_file_id)
);

ALTER TABLE feed_files ADD CONSTRAINT fk_feed_files_feed_id FOREIGN KEY (feed_id) REFERENCES t_feeds(feed_id);

ALTER TABLE experience_files ADD CONSTRAINT fk_experience_files_experience_id FOREIGN KEY (experience_id) REFERENCES experience(experience_id);
ALTER TABLE t_feeds CHANGE COLUMN file_id file_id BIGINT NULL ;
ALTER TABLE experience CHANGE COLUMN file_id file_id BIGINT NULL ;

ALTER TABLE socail_call_request RENAME TO  social_call_request;
ALTER TABLE social_call_request ADD UNIQUE unique_index(social_id, requester_profile_id);

ALTER TABLE social_report CHANGE COLUMN reportDescription report_description VARCHAR(191) NOT NULL ;


ALTER TABLE experience_likes ADD UNIQUE unique_index(experience_id, liker_profile_id);
ALTER TABLE feed_likes ADD UNIQUE unique_index(feed_id, liker_profile_id);
ALTER TABLE t_social_post_like ADD UNIQUE unique_index(social_id, liker_profile_id);

  CREATE TABLE experience_views_count (
  views_count_id bigint NOT NULL,
  profile_id bigint NOT NULL,
  experience_id bigint NOT NULL,
  PRIMARY KEY (views_count_id),
  KEY Unique_Index (profile_id,experience_id)
) 

ALTER TABLE t_feeds CHANGE COLUMN description description VARCHAR(1000) NULL DEFAULT NULL ;
ALTER TABLE t_social CHANGE COLUMN description description VARCHAR(1000) NULL DEFAULT NULL ;
ALTER TABLE experience CHANGE COLUMN description description TEXT NOT NULL ;

ALTER TABLE community CHANGE COLUMN description description VARCHAR(500) NOT NULL, CHANGE COLUMN rules rules VARCHAR(1000) NULL DEFAULT NULL ;
ALTER TABLE channel CHANGE COLUMN description description VARCHAR(500) NOT NULL, CHANGE COLUMN rules rules VARCHAR(1000) NULL DEFAULT NULL ;
ALTER TABLE channel ADD COLUMN is_private TINYINT(1) NULL DEFAULT 0 AFTER rules;

ALTER TABLE t_feeds_send_to ADD COLUMN name VARCHAR(191) NULL AFTER feed_id, CHANGE COLUMN type type VARCHAR(191) NOT NULL ;
