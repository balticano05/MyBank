WITH
inserted_role AS (
INSERT INTO roles (id, title, description)
VALUES (
    gen_random_uuid(),
    'ADMIN',
    'Administrator role with full access'
    )
ON CONFLICT (title) DO NOTHING
    RETURNING id
    ),

    inserted_credential AS (
INSERT INTO credentials (id, login, email, password)
VALUES (
    gen_random_uuid(),
    'admin',
    'admin@example.com',
    '$2a$10$sPjbnb8n2Ls4TBffCo9VHO9fE4s5qnPjk78Z3.DPsreVWElQgSdGe'
    )
    RETURNING id
    ),

    inserted_user AS (
INSERT INTO users (id, first_name, last_name, phone_number, credential_id, address, date_of_birth)
SELECT
    gen_random_uuid(),
    'Admin',
    'User',
    '+1234567890',
    id,
    '123 Admin St, Admin City',
    '1980-01-01'
FROM inserted_credential
    )

INSERT INTO credentials_roles (credential_id, role_id)
SELECT
    ic.id,
    (SELECT id FROM roles WHERE title = 'ADMIN')
FROM inserted_credential ic;