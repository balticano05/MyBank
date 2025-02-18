CREATE TABLE roles (
    id UUID PRIMARY KEY,
    title VARCHAR(32) UNIQUE NOT NULL,
    description TEXT
);