-- Passwords are similar to names with a small letter.
insert into users (name, surname, email, phone, role, status, password)
values ('Admin', 'Admin', 'admin@mail.ru', '+7 123 456 78 90', 'ADMIN', 'ACTIVE', '$2a$10$wstfwqiZ.OVdU53PbR5mo.ps.2.Xi8kbIGT4x3nKtrPVuJnP1Knxi'),
       ('Manager', 'Manager', 'manager@gmail.com', '+7 987 654 32 10', 'MANAGER', 'ACTIVE', '$2a$10$9hZkuyNJ/TJdtYNO/xg/AuJ3GQG3LHDV5gh5ZG0lFW9eG7MmUKY62'),
       ('User', 'User', 'user@mail.ru', null, 'USER', 'ACTIVE', '$2a$10$aIMKDB2XgWaAw9yMiv86C.iPM7mAjkB6n9THbaLXZTSDim521AvNy');

insert into carts (user_id, total_sum)
values (1, 0.00), (2, 0.00), (3, 0.00);