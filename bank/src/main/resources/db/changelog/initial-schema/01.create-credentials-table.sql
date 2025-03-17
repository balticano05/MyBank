CREATE TABLE credentials (
    id UUID PRIMARY KEY,
    login VARCHAR(32) UNIQUE NOT NULL,
    email VARCHAR(254) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
);

CREATE INDEX idx_credentials_email ON credentials(email);