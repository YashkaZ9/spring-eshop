insert into users (id, name, surname, email, phone, role, username, password)
values (1, 'admin', 'admin', 'admin@mail.ru', '+7 123 456 78 90', 'ADMIN', 'admin', 'admin');

alter sequence user_seq restart with 2;