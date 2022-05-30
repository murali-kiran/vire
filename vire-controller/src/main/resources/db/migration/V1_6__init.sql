
UPDATE master SET master_type='Personal_Profile_Setting_Type' WHERE master_type='Profile_Setting_Type';

replace into master (master_id, master_type, master_value, created_time, updated_time) values (3460, 'Firm_Profile_Setting_Type', 'Contact Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3461, 'Firm_Profile_Setting_Type', 'Address Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));
replace into master (master_id, master_type, master_value, created_time, updated_time) values (3462, 'Firm_Profile_Setting_Type', 'Work Details', (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)),(SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)));