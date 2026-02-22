ALTER TABLE user_profiles DROP CONSTRAINT user_profiles_pkey;
ALTER TABLE user_profiles ADD PRIMARY KEY (user_id);
ALTER TABLE user_profiles DROP COLUMN IF EXISTS id;
DROP SEQUENCE IF EXISTS user_profile_sequence;
