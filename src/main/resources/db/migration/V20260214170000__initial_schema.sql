CREATE SEQUENCE user_sequence START 1 INCREMENT 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_sequence'),
    username VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    keycloak_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    is_email_verified BOOLEAN DEFAULT FALSE,
    email_verified_at TIMESTAMP,
    email_verification_token VARCHAR(255),
    email_verification_token_expires_at TIMESTAMP,
    phone_number VARCHAR(255) UNIQUE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100),
    last_login_at TIMESTAMP,
    role INT NOT NULL DEFAULT 1
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_keycloak_id ON users(keycloak_id);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_email_verification_token ON users(email_verification_token);
CREATE INDEX idx_users_phone_number ON users(phone_number);

CREATE SEQUENCE user_profile_sequence START 1 INCREMENT 1;

CREATE TABLE user_profiles (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_profile_sequence'),
    user_id BIGINT UNIQUE NOT NULL,
    country_code VARCHAR(3) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender INT NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,

    CONSTRAINT fk_user_profiles_users
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_user_profiles_user_id ON user_profiles(user_id);