with rl as (
    select role_id as id
    from "Roles"
    where name = 'admin'
)
insert into "Users" (username, isu_id, role_id, password)
values 
    ('kek', 335047, rl.id, 'kek'),
    ('lol', 335039, rl.id, 'lol')
returning user_id;