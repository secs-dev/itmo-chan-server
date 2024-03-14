/*
 Roles
 */

INSERT INTO "Roles" (role_id, name, description)
SELECT 8, 'ADMIN', 'The coolest role ever'
WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 8
    );

INSERT INTO "Roles" (role_id, name, description)
SELECT 4, 'MODERATOR', 'Provides moderating comments'
    WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 4
    );

INSERT INTO "Roles" (role_id, name, description)
SELECT 2, 'USER', 'User role'
    WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 2
    );

INSERT INTO "Roles" (role_id, name, description)
SELECT 1, 'GUEST', 'Guest role'
    WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 1
    );
