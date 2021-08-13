insert into us_user (active, created_by, created_on, email, password, user_name)
values ('false', 1, current_timestamp, 'null', 'null', 'default');

insert into us_user (active, created_by, created_on, email, password, user_name)
values ('true', 1, current_timestamp, 'admin@home.in', 'password', 'admin');

insert into us_role (active, created_by, created_on, role_name)
values ('true', 1, current_timestamp, 'admin');

insert into us_users_roles (us_user_id, us_role_id)
values (2, 1);