insert into users (id, name, surname, email, phone, role, username, password)
values (1, 'admin', 'admin', 'admin@mail.ru', '+7 123 456 78 90', 'ADMIN', 'admin', '$2a$10$42GDuH0m31LRMVanGcKvXeh3gD1fqSDm2lljriZPKsT.eLpXcKdKK');

alter sequence user_seq restart with 2;