SELECT * FROM forum.users_password where dpassword is not null order by user_id;

SELECT * FROM forum.users_password where user_id=192;

SELECT * FROM forum.users_password where email like "%.pl%" and dpassword is not null;

SELECT max(user_id)+1 from forum.users_password;



select user_id, username, email, password, salt from forum.users_password where dpassword is not null;

-- insert into forum.users_password (user_id,username,password,salt,email)
-- select u.id, u.username,u.password,u.salt,u.email
-- from forum.users u
-- where u.id!= 1;