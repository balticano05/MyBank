CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(55) NOT NULL,
    last_name VARCHAR(30),
    phone_number VARCHAR(16) UNIQUE,
    credential_id UUID UNIQUE,
    address TEXT NOT NULL,
    date_of_birth DATE,

    CONSTRAINT fk_users_сredential_id FOREIGN KEY (credential_id) REFERENCES credentials(id)
);

CREATE INDEX idx_users_fullname ON users(first_name, last_name);
CREATE INDEX idx_users_address ON users(address);
CREATE INDEX idx_users_phone_number ON users(phone_number);