/* add_topic before */
with c as (
    insert into "Comments" (content, user_id)
	select 'Kek. Some interesting content', u.id
	from (
        select user_id as id
        from "Users"
        where username = 'kek'
    ) as u
    returning comment_id as id
), t as (
    select topic_id as id
    from "Topics"
    where name = 'itmo'
)
insert into "Threads" (topic_id, init_comment_id)
select t.id, c.id
from t, c
returning thread_id;