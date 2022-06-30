create sequence cart_seq start 1 increment 1;
create sequence category_seq start 1 increment 1;
create sequence order_contents_seq start 1 increment 1;
create sequence order_seq start 1 increment 1;
create sequence product_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;

create table cart_contents
(
    cart_id    int8 not null,
    product_id int8 not null
);

create table carts
(
    id      int8 not null,
    user_id int8,
    primary key (id)
);

create table categories
(
    id    int8 not null,
    title varchar(255),
    primary key (id)
);

create table order_contents
(
    id         int8 not null,
    price      numeric(19, 2),
    quantity   numeric(19, 2),
    order_id   int8,
    product_id int8,
    primary key (id)
);

create table orders
(
    id      int8 not null,
    address varchar(255),
    status  varchar(255),
    created timestamp,
    updated timestamp,
    user_id int8,
    sum     numeric(19, 2),
    comment varchar(255),
    primary key (id)
);

create table products
(
    id          int8 not null,
    title       varchar(255),
    price       float8,
    description varchar(255),
    image       oid,
    primary key (id)
);

create table users
(
    id       int8 not null,
    name     varchar(255),
    surname  varchar(255),
    email    varchar(255),
    phone    varchar(255),
    role     varchar(255),
    username varchar(255),
    password varchar(255),
    primary key (id)
);

create table products_categories
(
    product_id  int8 not null,
    category_id int8 not null
);

alter table if exists cart_contents add constraint fk_product_id foreign key (product_id) references products;
alter table if exists cart_contents add constraint fk_cart_id foreign key (cart_id) references carts;
alter table if exists carts add constraint fk_user_id foreign key (user_id) references users;
alter table if exists order_contents add constraint fk_order_id foreign key (order_id) references orders;
alter table if exists order_contents add constraint fk_product_id foreign key (product_id) references products;
alter table if exists orders add constraint fk_user_id foreign key (user_id) references users;
alter table if exists products_categories add constraint fk_category_id foreign key (category_id) references categories;
alter table if exists products_categories add constraint fk_product_id foreign key (product_id) references products;