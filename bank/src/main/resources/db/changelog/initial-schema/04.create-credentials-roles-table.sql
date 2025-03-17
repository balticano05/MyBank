CREATE TABLE credentials_roles (
    credential_id UUID,
    role_id UUID,
    PRIMARY KEY (credential_id, role_id),

    CONSTRAINT fk_credentials_roles_credential_id FOREIGN KEY (credential_id) REFERENCES credentials(id),
    CONSTRAINT fk_credentials_roles_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);