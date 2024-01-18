with rl as (
    select role_id as id
    from "Roles"
    where name = 'anonimous'
)
insert into "Users" (username, isu_id, role_id, password)
values 
    ('idk', 133226, rl.id, 'idk'),
    ('etc', 345890, rl.id, 'etc'),
    ('aka', 133226, rl.id, 'aka'),
    ('omg', 335059, rl.id, 'omg')
returning user_id;