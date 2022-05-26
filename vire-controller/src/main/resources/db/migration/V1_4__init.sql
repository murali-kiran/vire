CREATE TABLE social_category_send_to_master (
    social_category_send_to_master_id BIGINT NOT NULL,
    category_send_to VARCHAR(191) NOT NULL,
    social_category_master_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (social_category_send_to_master_id)
);

CREATE TABLE social_category_type_master (
    social_category_type_master_id BIGINT NOT NULL,
    category_type VARCHAR(191) NOT NULL,
    social_category_master_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (social_category_type_master_id)
);

CREATE TABLE profile_settings (
    profile_settings_id BIGINT NOT NULL,
    setting_type VARCHAR(191) NOT NULL,
    is_enabled TINYINT(1) NOT NULL,
    profile_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (profile_settings_id)
);

CREATE TABLE social_category_master (
    social_category_master_id BIGINT NOT NULL,
    category VARCHAR(191) NOT NULL,
    color_code VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (social_category_master_id)
);


ALTER TABLE profile_settings ADD CONSTRAINT fk_profile_settings_profile_id FOREIGN KEY (profile_id) REFERENCES profile(profile_id);

ALTER TABLE social_category_type_master ADD CONSTRAINT fk_social_category_type_master_social_category_master_id FOREIGN KEY (social_category_master_id) REFERENCES social_category_master(social_category_master_id);

ALTER TABLE social_category_send_to_master ADD CONSTRAINT fk_social_category_send_to_master_social_category_master_id FOREIGN KEY (social_category_master_id) REFERENCES social_category_master(social_category_master_id);
