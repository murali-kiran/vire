create table profile (
  profile_id bigint(20) not null,
  aadhar varchar(255) not null,
  email_id varchar(255) not null,
  is_aadhar_verified varchar(255) not null,
  mobile_number varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  primary key (profile_id)
);

create table personal_profile (
  profile_id bigint(20) not null,
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
  primary key (profile_id)
);

create table personal_profile_interest (
  personal_profile_interest_id bigint(20) not null,
  interest varchar(255) not null,
  personal_profile_profile_id bigint(20) default null,
  primary key (personal_profile_interest_id)
);

create table address (
  address_id bigint(20) not null,
  village_town_city varchar(255) not null,
  district varchar(255) not null,
  latitude double default null,
  longitude double default null,
  state varchar(255) not null,
  primary key (address_id)
);

create table firm_profile (
  profile_id bigint(20) not null,
  field_of_business varchar(255) not null,
  firm_address varchar(255) default null,
  product_or_service varchar(255) not null,
  firm_address_id bigint(20) not null,
  primary key (profile_id)
);

create table t_social (
     id bigint not null auto_increment,
	 user_id bigint not null,
	 category_id bigint not null,
	 type varchar(191),
     subject varchar(191),
	 description varchar(191),
	 contact varchar(20),
	 alternate_contact varchar(20),
	 image_path varchar(191),
     created_time bigint not null,
     updated_time bigint not null,
     primary key (id)
);
create table t_social_post_send_to (
     id bigint not null auto_increment primary key,
	 type varchar(191),
     value varchar(191),
     social_id bigint,
     constraint fk_t_social foreign key (social_id)
     references t_social(id)
);
create table t_social_post_chat (
     id bigint not null auto_increment,
	 chat_initiator_profile_id bigint not null,
	 sender_profile_id bigint not null,
	 message varchar(191),
     social_post_id bigint not null,
     chat_time bigint not null,
	 primary key (id)
);
create table t_social_post_comment (
     id bigint not null auto_increment,
	 commenter_profile_id bigint not null,
	 comment varchar(191),
     social_post_id bigint not null,
	 comment_time bigint not null,
	 primary key (id)
);
create table t_social_post_comment_reply (
     id bigint not null auto_increment,
	 comment_replier_profile_id bigint not null,
	 reply varchar(191),
	 comment_id bigint not null,
     reply_time bigint not null,
	 primary key (id)
);
create table t_social_post_like (
     id bigint not null auto_increment,
	 liker_profile_id bigint not null,
	 social_post_id bigint not null,
	 liked_time bigint not null,
	 primary key (id)
);
create table t_social_image (
  social_image_id bigint not null auto_increment,
  mime_type varchar(45) not null,
  image_path varchar(191) not null,
  image_size bigint not null,
  primary key (social_image_id)
);