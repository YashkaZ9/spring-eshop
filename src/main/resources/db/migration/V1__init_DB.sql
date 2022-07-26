create sequence categories_seq start 1 increment 1;
create sequence orders_products_seq start 1 increment 1;
create sequence orders_seq start 1 increment 1;
create sequence products_seq start 1 increment 1;
create sequence users_seq start 1 increment 1;

create table orders_products
(
    order_id   int8 unique    not null,
    product_id int8 unique    not null,
    price      numeric(19, 2) not null,
    quantity   numeric(19, 2) not null,
    primary key (order_id, product_id)
);

create table orders
(
    id      int8 unique    not null,
    user_id int8           not null,
    address varchar(255)   not null,
    status  varchar(50)    not null,
    created timestamp      not null,
    updated timestamp,
    sum     numeric(19, 2) not null,
    comment varchar(255),
    primary key (id)
);

create table categories
(
    id    int8 unique         not null,
    title varchar(255) unique not null,
    primary key (id)
);

create table products
(
    id          int8 unique         not null,
    title       varchar(255) unique not null,
    price       numeric(19, 2)      not null,
    description varchar(255),
    image       oid,
    primary key (id)
);

create table users
(
    id       int8 unique         not null,
    name     varchar(255),
    surname  varchar(255),
    email    varchar(255) unique not null,
    phone    varchar(255) unique,
    role     varchar(255)        not null,
    username varchar(255) unique not null,
    password varchar(255)        not null,
    primary key (id)
);

create table products_categories
(
    product_id  int8 unique not null,
    category_id int8 unique not null,
    primary key (product_id, category_id)
);

alter table if exists order_products add constraint fk_order_id foreign key (order_id) references orders;
alter table if exists order_products add constraint fk_product_id foreign key (product_id) references products;
alter table if exists orders add constraint fk_user_id foreign key (user_id) references users;
alter table if exists products_categories add constraint fk_category_id foreign key (category_id) references categories;
alter table if exists products_categories add constraint fk_product_id foreign key (product_id) references products;