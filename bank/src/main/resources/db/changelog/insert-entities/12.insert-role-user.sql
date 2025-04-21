INSERT INTO roles (id, title, description)
VALUES (gen_random_uuid(), 'USER', 'Role for simple users'),
       (gen_random_uuid(), 'ADMIN', 'Role for administrations'),
       (gen_random_uuid(), 'CONSULTANT', 'Role for consultants');