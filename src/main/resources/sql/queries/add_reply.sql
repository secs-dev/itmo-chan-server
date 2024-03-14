with c1 as (
    insert into "Comments" (content, user_id)
	select 'Idk. Some interesting content', u.id
	from (
        select user_id as id
        from "Users"
        where username = 'idk'
    ) as u
    returning comment_id as id
), c2 as (
    insert into "Comments" (content, user_id)
	select 'Etc. Reply Idk.' , u.id
	from (
        select user_id as id
        from "Users"
        where username = 'etc'
    ) as u
    returning comment_id as id
)
insert into "Replies" (comment_id, reply_comment_id)
select c1.id, c2.id
from c1, c2;