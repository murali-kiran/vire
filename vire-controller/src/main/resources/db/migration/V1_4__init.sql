ALTER TABLE profile ADD COLUMN profile_type VARCHAR(45) NULL AFTER weightage;
ALTER TABLE profile CHANGE COLUMN aadhar aadhar VARCHAR(45) NULL;
ALTER TABLE profile CHANGE COLUMN is_aadhar_verified is_aadhar_verified TINYINT(1) NULL DEFAULT 0 ;
ALTER TABLE personal_profile CHANGE COLUMN blood_donate_willingness blood_donate_willingness VARCHAR(45) NULL;

ALTER TABLE firm_profile CHANGE COLUMN field_of_business field_of_business VARCHAR(255) NULL;
ALTER TABLE firm_profile CHANGE COLUMN product_or_service product_or_service VARCHAR(255) NULL;
ALTER TABLE firm_profile CHANGE COLUMN firm_address_id firm_address_id BIGINT NULL;