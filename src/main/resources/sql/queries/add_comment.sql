/* add_role before */
insert into "Comments" (thread_id, content, user_id)
select 'Lol. Some mega interesting content', u.id
from (
    select user_id as id
    from "Users"
    where username = 'lol'
) as u
returning comment_id as id;

insert into "Comments" (thread_id, content, user_id)
VALUES (1, 'Privet, Misha!', 7),
       (1, 'Privet, SuperHot!', 8),
       (1, 'Privet, SuperHot!', 8),
       (1, 'There is nothing', 6);

