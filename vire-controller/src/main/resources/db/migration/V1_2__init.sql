ALTER TABLE profile ADD COLUMN first_login VARCHAR(10) NOT NULL AFTER gender;
ALTER TABLE profile ADD COLUMN file_id varchar(191) NULL AFTER date_of_birth;
AlTER TABLE profile ADD UNIQUE (email_id, mobile_number);