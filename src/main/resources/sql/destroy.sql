/*drop trigger if exists after_inserting_comment on "Comments" cascade;
drop function if exists after_inserting_comment_reaction_set;

drop trigger if exists after_inserting_comment_popularity on "Comments" cascade;
drop function if exists update_popularity_in_thread;

drop trigger if exists after_inserting_comment_reaction_set on "Comments" cascade;
drop function if exists create_reaction_set_for_comment;

drop trigger if exists trash_insert_comment_trigger on "Trash" cascade;
drop function if exists delete_expired_trashed_comments;

drop procedure if exists append_reaction_to_comment;
drop procedure if exists throw_in_trash cascade;
drop procedure if exists vote_in_poll cascade;

drop table if exists "Users" cascade;
drop table if exists "Roles" cascade;
drop table if exists "Comments" cascade;
drop table if exists "Replies" cascade;
drop table if exists "Threads" cascade;
drop table if exists "Topics" cascade;
drop table if exists "Trash" cascade;
drop table if exists "Reaction_sets" cascade;
drop table if exists "Pictures" cascade;
drop table if exists "Picture_attachments" cascade;
drop table if exists "Videos" cascade;
drop table if exists "Video_attachments" cascade;
drop table if exists "Polls" cascade;
drop table if exists "Voted_users" cascade;
drop table if exists "Poll_answers" cascade;
drop table if exists "Captcha" cascade;
*/