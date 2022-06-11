--ALTER TABLE profile ALTER COLUMN date_of_birth SET null;
ALTER TABLE profile ALTER COLUMN aadhar SET DEFAULT null;
--ALTER TABLE profile ALTER COLUMN is_aadhar_verified SET DATA TYPE TINYINT(1);
ALTER TABLE profile MODIFY COLUMN is_aadhar_verified TINYINT(1) null;
--ALTER TABLE profile ALTER COLUMN is_aadhar_verified SET DEFAULT null;
ALTER TABLE personal_profile ALTER COLUMN blood_donate_willingness SET DEFAULT null;

ALTER TABLE firm_profile ALTER COLUMN field_of_business SET DEFAULT null;
ALTER TABLE firm_profile ALTER COLUMN product_or_service SET DEFAULT null;
ALTER TABLE firm_profile ALTER COLUMN firm_address_id  SET DEFAULT null;

ALTER TABLE profile ADD COLUMN profile_type varchar(50) null AFTER weightage;

