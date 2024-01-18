WITH t AS (
    DELETE
    FROM "Topics"
    WHERE name = 'cringe'
    RETURNING topic_id
), thr AS (
    SELECT thread_id
    FROM "Threads"
    WHERE topic_id = t.topic_id
) 
UPDATE "Comments"
SET deleted = TRUE
WHERE thread_id = thr.thread_id;
