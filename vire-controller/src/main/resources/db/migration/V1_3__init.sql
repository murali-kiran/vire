CREATE TABLE community_profile_files (
    community_profile_files_id BIGINT NOT NULL,
    community_profile_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (community_profile_files_id)
);

CREATE TABLE community_files (
    community_files_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    community_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (community_files_id)
);
ALTER TABLE community_profile_files ADD CONSTRAINT fk_community_profile_files_community_profile_id FOREIGN KEY (community_profile_id) REFERENCES community_profile(community_profile_id);
ALTER TABLE community_files ADD CONSTRAINT fk_community_files_community_id FOREIGN KEY (community_id) REFERENCES community(community_id);


ALTER TABLE channel ADD COLUMN cover_file_id BIGINT NOT NULL AFTER file_id, CHANGE COLUMN rules rules VARCHAR(20) NULL;
ALTER TABLE community_profile ADD UNIQUE unique_index(community_id, profile_id);
ALTER TABLE channel_profile ADD UNIQUE unique_index(channel_id, profile_id);