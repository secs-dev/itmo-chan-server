insert into "Roles" (name, description)
values 
    ('admin', 'The coolest role ever'),
    ('moderator', 'Provides moderating comments'),
    ('guest', 'Guest role')
returning role_id;

returning role_id;