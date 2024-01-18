WITH r AS (
    SELECT reactions_id AS r_id
    FROM "Comments"
    WHERE comment_id = 15
)
UPDATE "Reactions_set"
SET reactions = '{}'::jsonb
WHERE r_set_id = r_id;