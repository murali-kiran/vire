CREATE TABLE `profile` (
  `profile_id` bigint(20) NOT NULL,
  `aadhar` varchar(255) NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `is_aadhar_verified` varchar(255) NOT NULL,
  `mobile_number` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`profile_id`)
);

CREATE TABLE `personal_profile` (
  `profile_id` bigint(20) NOT NULL,
  `blood_donate_willingness` varchar(255) NOT NULL,
  `blood_group` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `field_profession_business` varchar(255) DEFAULT NULL,
  `graduation_board` varchar(255) DEFAULT NULL,
  `graduation_college_name` varchar(255) DEFAULT NULL,
  `intermediate_board` varchar(255) DEFAULT NULL,
  `intermediate_college_name` varchar(255) DEFAULT NULL,
  `organization_location` varchar(255) DEFAULT NULL,
  `organization_name` varchar(255) DEFAULT NULL,
  `post_graduation_board` varchar(255) DEFAULT NULL,
  `post_graduation_college_name` varchar(255) DEFAULT NULL,
  `school_board` varchar(255) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `work_status` varchar(255) DEFAULT NULL,
  `permanent_address_id` bigint(20) NOT NULL,
  `present_address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`profile_id`),
  KEY `FK6n4yyaqurm4qp7f3bwext0t2s` (`permanent_address_id`),
  KEY `FKinx549xb8wcekbx4bfnccpuen` (`present_address_id`)
);

CREATE TABLE `personal_profile_interest` (
  `personal_profile_interest_id` bigint(20) NOT NULL,
  `interest` varchar(255) NOT NULL,
  `personal_profile_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`personal_profile_interest_id`),
  KEY `FK6kapbkiomr2hu1cc4oalre13t` (`personal_profile_profile_id`)
);

CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL,
  `village_town_city` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `state` varchar(255) NOT NULL,
  PRIMARY KEY (`address_id`)
);

CREATE TABLE t_social (
     id BIGINT NOT NULL AUTO_INCREMENT,
	 user_id BIGINT NOT NULL,
	 category_id BIGINT NOT NULL,
	 type VARCHAR(191),
     subject VARCHAR(191),
	 description VARCHAR(191),
	 contact VARCHAR(20),
	 alternate_contact VARCHAR(20),
	 image_path VARCHAR(191),
     created_time BIGINT NOT NULL,
     updated_time BIGINT NOT NULL,
     PRIMARY KEY (id)
);
CREATE TABLE t_social_post_send_to (
     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	 type VARCHAR(191),
     value VARCHAR(191),
     social_id BIGINT,
     CONSTRAINT fk_t_social FOREIGN KEY (social_id)
     REFERENCES t_social(id)
);
CREATE TABLE t_social_post_chat (
     id BIGINT NOT NULL AUTO_INCREMENT,
	 chat_initiator_profile_id BIGINT NOT NULL,
	 sender_profile_id BIGINT NOT NULL,
	 message VARCHAR(191),
     social_post_id BIGINT NOT NULL,
     chat_time BIGINT NOT NULL,
	 PRIMARY KEY (id)
);
CREATE TABLE t_social_post_comment (
     id BIGINT NOT NULL AUTO_INCREMENT,
	 commenter_profile_id BIGINT NOT NULL,
	 comment VARCHAR(191),
     social_post_id BIGINT NOT NULL,
	 comment_time BIGINT NOT NULL,
	 PRIMARY KEY (id)
);
CREATE TABLE t_social_post_comment_reply (
     id BIGINT NOT NULL AUTO_INCREMENT,
	 comment_replier_profile_id BIGINT NOT NULL,
	 reply VARCHAR(191),
	 comment_id BIGINT NOT NULL,
     reply_time BIGINT NOT NULL,
	 PRIMARY KEY (id)
);
CREATE TABLE t_social_post_like (
     id BIGINT NOT NULL AUTO_INCREMENT,
	 liker_profile_id BIGINT NOT NULL,
	 social_post_id BIGINT NOT NULL,
	 liked_time BIGINT NOT NULL,
	 PRIMARY KEY (id)
);
CREATE TABLE `t_social_image` (
  `social_image_id` BIGINT NOT NULL AUTO_INCREMENT,
  `mime_type` VARCHAR(45) NOT NULL,
  `image_path` VARCHAR(191) NOT NULL,
  `image_size` BIGINT NOT NULL,
  PRIMARY KEY (`social_image_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = 'social image details table';