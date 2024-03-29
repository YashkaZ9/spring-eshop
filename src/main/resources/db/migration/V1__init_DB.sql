create table categories
(
    id    bigint primary key generated by default as identity,
    title varchar(100) unique not null
);

create table products
(
    id          bigint primary key generated by default as identity,
    title       varchar(100) unique not null,
    price       numeric(19, 2)      not null check (price > 0),
    description varchar(255)
);

create table products_categories
(
    product_id  bigint references products (id) on delete cascade,
    category_id bigint references categories (id) on delete cascade,
    primary key (product_id, category_id)
);

create table images
(
    id           bigint primary key generated by default as identity,
    product_id   bigint       not null references products (id) on delete cascade,
    name         varchar(100) not null,
    size         bigint       not null,
    content_type varchar(100) not null,
    bytes        oid          not null
);

-- @Enumerated roles: USER, MANAGER, ADMIN

-- @Enumerated user_status: ACTIVE, BANNED

create table users
(
    id       bigint primary key generated by default as identity,
    email    varchar(100) unique not null,
    password varchar(100)        not null,
    name     varchar(100),
    surname  varchar(100),
    phone    varchar(100) unique,
    role     varchar(50)         not null default 'USER',
    status   varchar(50)         not null default 'ACTIVE'
);

create table carts
(
    id      bigint primary key generated by default as identity,
    user_id bigint not null unique references users (id) on delete cascade
);

create table cart_positions
(
    id         bigint generated by default as identity,
    cart_id    bigint not null references carts (id) on delete cascade,
    product_id bigint not null references products (id) on delete cascade,
    quantity   bigint not null check (quantity > 0),
    unique (cart_id, product_id)
);

-- @Enumerated order_status: NEW (user made order), ACTIVE (manager approved order),
-- CLOSED (user closed order. Constraints: order_status.ACTIVE and LocalDateTime.now() > order.plannedExecutionDate)

create table orders
(
    id                     bigint primary key generated by default as identity,
    user_id                bigint         not null references users (id) on delete cascade,
    total_sum              numeric(19, 2) not null check (total_sum >= 0),
    status                 varchar(50)    not null,
    created_at             timestamp      not null,
    updated_at             timestamp      not null,
    planned_execution_date timestamp,
    address                varchar(255)   not null,
    comment                varchar(255)
);

create table order_positions
(
    id            bigint generated by default as identity,
    order_id      bigint         not null references orders (id) on delete cascade,
    product_title varchar(100)   not null,
    quantity      bigint         not null check (quantity > 0),
    sum           numeric(19, 2) not null check (sum > 0)
);