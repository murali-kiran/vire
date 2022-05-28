CREATE TABLE socail_call_request (
    socail_call_request_id BIGINT NOT NULL,
    social_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    requester_profile_id BIGINT NOT NULL,
    status VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (socail_call_request_id)
);


ALTER TABLE socail_call_request ADD CONSTRAINT fk_socail_call_request_socail_id FOREIGN KEY (social_id) REFERENCES t_social(social_id);

replace into master (master_id, master_type, master_value, created_time, updated_time) values (3454, 'Profile_Setting_Type', 'Basic Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3455, 'Profile_Setting_Type', 'Contact Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3456, 'Profile_Setting_Type', 'Address Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3457, 'Profile_Setting_Type', 'Educational Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3458, 'Profile_Setting_Type', 'Professional Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3459, 'Profile_Setting_Type', 'Blood Donation Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
