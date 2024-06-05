create table product_table
(
    product_id          serial           not null
        constraint product_table_pk
            primary key,
    product_name        varchar(50)      not null,
    product_description varchar(50),
    product_image       text,
    product_price       double precision not null
);

create table user_table
(
    user_id       serial       not null
        constraint user_table_pk
            primary key,
    user_name     varchar(50)  not null,
    user_email    varchar(150) not null
        constraint user_table_pk_2
            unique,
    user_number   varchar(20),
    user_address  varchar(50),
    user_role     text,
    user_password varchar(300) not null
);

create table purchased_item_table
(
    purchase_id serial not null
        constraint purchased_item_table_pk
            primary key,
    product_id  integer
        constraint purchased_item_table_product_table_product_id_fk
            references product_table,
    quantity    integer default 1
);

create table order_table
(
    order_id    serial  not null
        constraint order_table_pk
            primary key,
    user_id     integer not null
        constraint order_table_user_table_user_id_fk
            references user_table,
    purchase_id integer not null
        constraint order_table_purchased_item_table_purchase_id_fk
            references purchased_item_table
);

