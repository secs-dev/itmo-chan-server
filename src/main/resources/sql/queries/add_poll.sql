with c as (
	insert into "Comments" (content, user_id)
	select 'Kek. Comment with poll 2', u.id
	from (
        select user_id as id
        from "Users"
        where username = 'kek'
    ) as u
    returning comment_id as id
) 
insert into "Polls" (comment_id, title)
select c.id, 'Very important poll 2'
from c
returning poll_id as id; /* idk how to insert multiple answers in single query */

insert into "Poll_answers" (poll_id, answer_title)
values 
    (1, 'yes'),   
    (1, 'no');