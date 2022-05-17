create table profile (
  profile_id bigint(20) not null,
  aadhar varchar(255) not null,
  email_id varchar(255) not null,
  is_aadhar_verified varchar(255) not null,
  mobile_number varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  created_time bigint not null,
  updated_time bigint not null,
  primary key (profile_id)
);

create table personal_profile (
  personal_profile_id bigint(20) not null,
  date_of_birth varchar(255) not null,
  gender varchar(255) not null,
  blood_donate_willingness varchar(255) not null,
  blood_group varchar(255) default null,
  designation varchar(255) default null,
  field_profession_business varchar(255) default null,
  graduation_board varchar(255) default null,
  graduation_college_name varchar(255) default null,
  intermediate_board varchar(255) default null,
  intermediate_college_name varchar(255) default null,
  organization_location varchar(255) default null,
  organization_name varchar(255) default null,
  post_graduation_board varchar(255) default null,
  post_graduation_college_name varchar(255) default null,
  school_board varchar(255) default null,
  school_name varchar(255) default null,
  work_status varchar(255) default null,
  permanent_address_id bigint(20) default null,
  present_address_id bigint(20) default null,
  created_time bigint not null,
  updated_time bigint not null,
  primary key (personal_profile_id),
  constraint fk_personal_profile foreign key (personal_profile_id) references profile(profile_id)
);

create table personal_profile_interest (
  personal_profile_interest_id bigint(20) not null,
  interest varchar(255) not null,
  personal_profile_id bigint(20) default null,
  created_time bigint not null,
  updated_time bigint not null,
  primary key (personal_profile_interest_id),
  constraint fk_personal_profile_interest foreign key (personal_profile_id) references personal_profile(personal_profile_id)
);

create table address (
  address_id bigint(20) not null,
  village_town_city varchar(255) not null,
  district varchar(255) not null,
  state varchar(255) not null,
  latitude double default null,
  longitude double default null,
  created_time bigint not null,
  updated_time bigint not null,
  primary key (address_id)
);
create table firm_profile (
  firm_profile_id bigint(20) not null,
  field_of_business varchar(255) not null,
  firm_address varchar(255) default null,
  product_or_service varchar(255) not null,
  firm_address_id bigint(20) not null,
  created_time bigint not null,
  updated_time bigint not null,
  primary key (firm_profile_id),
  constraint fk_firm_profile foreign key (firm_profile_id) references profile(profile_id)
);

create table t_social (
     social_id bigint not null,
	 profile_id bigint not null,
	 category_id bigint not null,
     file_id varchar(191),
	 type varchar(191),
     subject varchar(191),
	 description varchar(191),
	 contact varchar(20),
	 alternate_contact varchar(20),
	 share_contact tinyint,
	 share_alternate tinyint,
     created_time bigint not null,
     updated_time bigint not null,
     primary key (social_id)
);
create table t_social_post_send_to (
     social_post_send_to_id bigint not null,
	 type varchar(191),
     value varchar(191),
     social_id bigint not null,
     created_time bigint not null,
     updated_time bigint not null,
     primary key (social_post_send_to_id),
     constraint fk_t_social_send_to foreign key (social_id) references t_social(social_id)
);
create table t_social_post_chat (
     social_post_chat_id bigint not null,
	 chat_initiator_profile_id bigint not null,
	 sender_profile_id bigint not null,
	 message varchar(191),
     social_id bigint not null,
     created_time bigint not null,
     updated_time bigint not null,
	 primary key (social_post_chat_id),
	 constraint fk_t_social_post_chat foreign key (social_id) references t_social(social_id)
);
create table t_social_post_comment (
     social_post_comment_id bigint not null,
	 commenter_profile_id bigint not null,
	 comment varchar(191),
     social_id bigint not null,
     created_time bigint not null,
     updated_time bigint not null,
	 primary key (social_post_comment_id),
	 constraint fk_t_social_post_comment foreign key (social_id) references t_social(social_id)

);
create table t_social_post_comment_reply (
     social_post_comment_reply_id bigint not null,
	 comment_replier_profile_id bigint not null,
	 reply varchar(191),
	 comment_id bigint not null,
     social_id bigint not null,
     created_time bigint not null,
     updated_time bigint not null,
	 primary key (social_post_comment_reply_id),
	 constraint fk_t_social_comment_reply foreign key (social_id) references t_social(social_id),
     constraint fk_t_social_comment foreign key (comment_id) references t_social_post_comment(social_post_comment_id)

);
create table t_social_post_like (
     social_post_like_id bigint not null,
	 liker_profile_id bigint not null,
	 social_id bigint not null,
	 created_time bigint not null,
     updated_time bigint not null,
	 primary key (social_post_like_id),
	 constraint fk_t_social_post_like foreign key (social_id) references t_social(social_id)
);
create table t_file (
  file_id bigint not null,
  mime_type varchar(45) not null,
  file_common_path varchar(191) not null,
  file_size bigint not null,
  created_time bigint not null,
  updated_time bigint not null,
  primary key (file_id)
  );
create table t_feeds (
     feed_id bigint not null,
	 profile_id bigint not null,
	 description varchar(191),
	 file_id bigint not null,
     created_time bigint not null,
     updated_time bigint not null,
     primary key (feed_id)
);
create table t_feeds_send_to (
     t_feeds_send_to_id bigint not null,
	 type varchar(191),
     value varchar(191),
     feed_id bigint not null,
     created_time bigint not null,
     updated_time bigint not null,
     primary key (t_feeds_send_to_id),
     constraint fk_t_feed_send_to foreign key (feed_id) references t_feeds(feed_id)
);

CREATE TABLE feed_comment (
    feed_comment_id BIGINT NOT NULL,
    commentor_profile_id BIGINT NOT NULL,
    feed_id BIGINT NOT NULL,
    comment VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feed_comment_id)
);
CREATE TABLE feed_comment_reply (
    feed_comment_reply_id BIGINT NOT NULL,
    replier_profile_id BIGINT NOT NULL,
    feed_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    reply VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feed_comment_reply_id)
);

CREATE TABLE feed_likes (
    feed_likes_id BIGINT NOT NULL,
    liker_profile_id BIGINT NOT NULL,
    feed_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (feed_likes_id)
);

CREATE TABLE community_profile (
    community_profile_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    community_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (community_profile_id)
);

CREATE TABLE `t_master_data` (
  `master_data_id` BIGINT NOT NULL,
  `master_data_type` BIGINT NOT NULL,
  `master_data_value` BIGINT NOT NULL,
  `created_time` BIGINT NOT NULL,
  `updated_time` BIGINT NULL,
  PRIMARY KEY (`master_data_id`)
  );
CREATE TABLE community (
    community_id BIGINT NOT NULL,
    name VARCHAR(191) NOT NULL,
    description VARCHAR(20) NOT NULL,
    creator_profile_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    rules VARCHAR(20) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (community_id)
);
CREATE TABLE master (
    master_id BIGINT NOT NULL,
    master_type VARCHAR(191) NOT NULL,
    master_value VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (master_id)
);
CREATE TABLE experience (
    experience_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    title VARCHAR(191) NOT NULL,
    description VARCHAR(191) NOT NULL,
    location VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (experience_id)
);

CREATE TABLE experience_comment (
    experience_comment_id BIGINT NOT NULL,
    commentor_profile_id BIGINT NOT NULL,
    experience_id BIGINT NOT NULL,
    comment VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (experience_comment_id)
);

CREATE TABLE experience_likes (
    experience_likes_id BIGINT NOT NULL,
    liker_profile_id BIGINT NOT NULL,
    experience_id BIGINT NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (experience_likes_id)
);

CREATE TABLE experience_comment_reply (
    experience_comment_reply_id BIGINT NOT NULL,
    replier_profile_id BIGINT NOT NULL,
    experience_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    reply VARCHAR(191) NOT NULL,
    created_time BIGINT NOT NULL,
    updated_time BIGINT NOT NULL,
    PRIMARY KEY (experience_comment_reply_id)
);
  ALTER TABLE community_profile ADD CONSTRAINT fk_community_profile_community_id FOREIGN KEY (community_id) REFERENCES community(community_id);
