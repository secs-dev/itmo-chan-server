/*
 Roles
 */

INSERT INTO "Roles" (role_id, name, description)
SELECT 8, 'admin', 'The coolest role ever'
WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 8
    );

INSERT INTO "Roles" (role_id, name, description)
SELECT 4, 'moderator', 'Provides moderating comments'
    WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 4
    );

INSERT INTO "Roles" (role_id, name, description)
SELECT 2, 'user', 'User role'
    WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 2
    );

INSERT INTO "Roles" (role_id, name, description)
SELECT 1, 'guest', 'Guest role'
    WHERE
    NOT EXISTS (
        SELECT role_id FROM "Roles" WHERE role_id = 1
    );
