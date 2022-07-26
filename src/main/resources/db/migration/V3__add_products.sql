insert into products(id, title, price)
values (1, 'Bread', 45),
       (2, 'Milk', 80),
       (3, 'Water', 30),
       (4, 'Banana', 79),
       (5, 'Cheese', 730);

alter sequence products_seq restart with 6;