/* tables */

CREATE TABLE IF NOT EXISTS "Users" (
                         user_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         username varchar(255) NOT NULL UNIQUE,
                         isu_id integer NULL UNIQUE,
                         permissions integer NOT NULL DEFAULT 1, -- permissions is sum of roles id -- TODO Maybe future problem with max int postgres (31 roles) but it looks pretty nice as for me if change to bigint
                         password varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "Roles" (
                         role_id integer PRIMARY KEY,
                         name varchar(255) NOT NULL UNIQUE,
                         description text NOT NULL
);

CREATE TABLE IF NOT EXISTS "Comments" (
                            comment_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                            thread_id integer NOT NULL,
                            title varchar(255),
                            content text NOT NULL,
                            user_id integer NOT NULL,
                            reactions_id integer,
                            creation_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            trashed bool NOT NULL DEFAULT FALSE,
                            deleted bool NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS  "Replies" (
                           comment_id integer NOT NULL,
                           reply_comment_id integer NOT NULL,
                           PRIMARY KEY (comment_id, reply_comment_id)
);

CREATE TABLE IF NOT EXISTS  "Threads" (
                           thread_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           topic_id integer NOT NULL,
                           init_comment_id integer NULL,
                           popularity INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS  "Topics" (
                          topic_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          name varchar(255) NOT NULL,
                          description text
);

CREATE TABLE IF NOT EXISTS  "Trash" (
                         trash_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         comment_id integer NOT NULL,
                         reason text,
                         recycle_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS  "Reaction_sets" (
                                 r_set_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                 reactions jsonb NOT NULL DEFAULT '{}'::jsonb
);

CREATE TABLE IF NOT EXISTS  "Files" (
                          file_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
                          name varchar(255) NOT NULL,
                          content_type varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS  "File_attachments" (
                                     comment_id integer NOT NULL,
                                     file_id uuid NOT NULL,
                                     PRIMARY KEY (comment_id, file_id)
);

CREATE TABLE IF NOT EXISTS  "Polls" (
                         poll_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         comment_id integer NOT NULL,
                         title varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS  "Voted_users" (
                               poll_id integer NOT NULL,
                               user_id integer NOT NULL,
                               PRIMARY KEY (poll_id, user_id)
);

CREATE TABLE IF NOT EXISTS  "Poll_answers" (
                                poll_answer_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                poll_id integer NOT NULL,
                                answer_title varchar(255) NOT NULL,
                                votes_number integer NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS  "Captcha" (
                           captcha_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           answer varchar(255),
                           file_id uuid NOT NULL
);

/* constraints */

/* Users */

ALTER TABLE "Users" DROP CONSTRAINT IF EXISTS "username_minimal_length";
ALTER TABLE "Users"
    ADD CONSTRAINT "username_minimal_length"
        CHECK (length(username) >= 3);

ALTER TABLE "Users" DROP CONSTRAINT IF EXISTS "isu_id_range";
ALTER TABLE "Users"
    ADD CONSTRAINT "isu_id_range"
        CHECK (isu_id > 0);

/* Comments */
/*TODO FIX ADD FK WITH VALIDATION THAN DOESN'T EXISTS*/
ALTER TABLE "Comments"
    ADD FOREIGN KEY (reactions_id)
        REFERENCES "Reaction_sets" (r_set_id)
        ON DELETE SET NULL;

ALTER TABLE "Comments"
    ADD FOREIGN KEY (user_id)
        REFERENCES "Users" (user_id)
        ON DELETE RESTRICT;

ALTER TABLE "Comments"
    ADD FOREIGN KEY (thread_id)
        REFERENCES "Threads" (thread_id)
        ON DELETE RESTRICT;

/* Replies */

ALTER TABLE "Replies"
    ADD FOREIGN KEY (comment_id)
        REFERENCES "Comments" (comment_id)
        ON DELETE NO ACTION;

ALTER TABLE "Replies"
    ADD FOREIGN KEY (reply_comment_id)
        REFERENCES "Comments" (comment_id)
        ON DELETE NO ACTION;

/* Threads */

ALTER TABLE "Threads"
    ADD FOREIGN KEY (topic_id)
        REFERENCES "Topics" (topic_id)
        ON DELETE RESTRICT;

ALTER TABLE "Threads"
    ADD FOREIGN KEY (init_comment_id)
        REFERENCES "Comments" (comment_id)
        ON DELETE RESTRICT;

/* Trash */

ALTER TABLE "Trash"
    ADD FOREIGN KEY (comment_id)
        REFERENCES "Comments" (comment_id)
        ON DELETE NO ACTION;

/* Captcha */

ALTER TABLE "Captcha"
    ADD FOREIGN KEY (file_id)
        REFERENCES "Files" (file_id)
        ON DELETE CASCADE;

/* File_attachments */

ALTER TABLE "File_attachments"
    ADD FOREIGN KEY (file_id)
        REFERENCES "Files" (file_id)
        ON DELETE CASCADE;

ALTER TABLE "File_attachments"
    ADD FOREIGN KEY (comment_id)
        REFERENCES "Comments" (comment_id)
        ON DELETE CASCADE;

/* Polls */

ALTER TABLE "Polls"
    ADD FOREIGN KEY (comment_id)
        REFERENCES "Comments" (comment_id)
        ON DELETE CASCADE;

/* Voted_users */

ALTER TABLE "Voted_users"
    ADD FOREIGN KEY (poll_id)
        REFERENCES "Polls" (poll_id)
        ON DELETE CASCADE;

ALTER TABLE "Voted_users"
    ADD FOREIGN KEY (user_id)
        REFERENCES "Users" (user_id)
        ON DELETE CASCADE;
/*
ALTER TABLE "Voted_users"
    ADD PRIMARY KEY (poll_id, user_id);
*/
/* Poll_answers */

ALTER TABLE "Poll_answers"
    ADD FOREIGN KEY (poll_id)
        REFERENCES "Polls" (poll_id)
        ON DELETE CASCADE;

/* functions */

CREATE OR REPLACE PROCEDURE append_reaction_to_comment(reaction_text varchar, c_id bigint)
AS '
    DECLARE
r_id bigint;
        r_cnt bigint;
BEGIN
        IF length(reaction_text) < 7 THEN
SELECT reactions_id
INTO r_id
FROM "Comments"
WHERE "Comments".comment_id = append_reaction_to_comment.c_id;

SELECT reactions -> reaction_text
into r_cnt
FROM "Reaction_sets"
WHERE r_set_id = r_id;

IF r_cnt IS NULL THEN
                r_cnt := 0;
END IF;

UPDATE "Reaction_sets"
SET reactions = jsonb_set(reactions, array[reaction_text], to_jsonb(r_cnt + 1), TRUE)
WHERE r_set_id = r_id;
ELSE
            RAISE EXCEPTION $$Reaction is too long$$;
END IF;
END;
' LANGUAGE PLPGSQL;

/* EXAMPLE
  call append_reaction_to_comment('tl;dr',  15)
*/

CREATE OR REPLACE PROCEDURE throw_in_trash(comment_id bigint, reason text)
AS '
    INSERT INTO "Trash"(comment_id, reason) VALUES(comment_id, reason);
UPDATE "Comments"
SET trashed = true
WHERE comment_id = throw_in_trash.comment_id;
' LANGUAGE SQL;

/* EXAMPLE
   CALL throw_in_trash(5, 'You made a mistake when wrote there smth');
*/

CREATE OR REPLACE PROCEDURE vote_in_poll(user_id_arg bigint, poll_id_arg bigint, answer_ids_arg bigint[])
AS '
    DECLARE
answer bigint;
BEGIN
        FOREACH answer IN ARRAY answer_ids_arg
        LOOP
UPDATE "Poll_answers"
SET votes_number = votes_number + 1
WHERE poll_answer_id = answer AND poll_id = poll_id_arg;
END LOOP;
INSERT INTO "Voted_users"(poll_id, user_id) VALUES(poll_id_arg, user_id_arg);
END;
' LANGUAGE PLPGSQL;

/* EXAMPLE
   CALL vote_in_poll(5, 2, array[1,2]);
*/

/* triggers */

CREATE OR REPLACE FUNCTION create_reaction_set_for_comment()
RETURNS TRIGGER
AS
'
    DECLARE
r_id integer := 0;
BEGIN
        IF (NEW.reactions_id IS NULL) THEN
            with tempa as (
                INSERT INTO "Reaction_sets"
                    DEFAULT VALUES
                    RETURNING r_set_id as id
            )
SELECT SUM(tempa.id) INTO r_id
FROM tempa;
UPDATE "Comments"
SET reactions_id = r_id
where comment_id = NEW.comment_id;
END IF;
RETURN NEW;
END
' LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION update_popularity_in_thread()
    RETURNS TRIGGER
AS
'
DECLARE
BEGIN
UPDATE "Threads"
SET popularity = popularity + 1
WHERE thread_id = NEW.thread_id;
RETURN NEW;
END
' LANGUAGE PLPGSQL;

CREATE OR REPLACE TRIGGER after_inserting_comment_reaction_set
    AFTER INSERT ON "Comments"
    FOR EACH ROW EXECUTE PROCEDURE create_reaction_set_for_comment();

CREATE OR REPLACE TRIGGER after_inserting_comment_popularity
    AFTER INSERT ON "Comments"
    FOR EACH ROW EXECUTE PROCEDURE update_popularity_in_thread();

CREATE OR REPLACE FUNCTION delete_expired_trashed_comments() RETURNS TRIGGER
AS '
BEGIN
UPDATE "Comments"
SET deleted = true
WHERE comment_id IN
      (SELECT comment_id FROM "Trash"
       WHERE recycle_date < NOW() - INTERVAL $$1 hour$$);
RETURN NEW;
END;
' LANGUAGE PLPGSQL;

CREATE OR REPLACE TRIGGER  trash_insert_comment_trigger
    AFTER INSERT ON "Trash"
    EXECUTE PROCEDURE delete_expired_trashed_comments();