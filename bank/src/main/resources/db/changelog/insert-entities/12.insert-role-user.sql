INSERT INTO roles (id, title, description)
VALUES (random_uuid(), 'USER', 'Role for simple users'),
       (random_uuid(), 'ADMIN', 'Role for administrations'),
       (random_uuid(), 'CONSULTANT', 'Role for Consultants');