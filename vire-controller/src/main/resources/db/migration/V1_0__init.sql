create table profiles(id bigint primary key, user_id bigint, name varchar(191));


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