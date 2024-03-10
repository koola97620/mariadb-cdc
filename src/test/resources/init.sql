create table test.menu
(
    id            bigint not null auto_increment,
    displayed     bit            not null,
    name          varchar(255)   not null,
    price         int(10) not null,
    menu_group_id bigint not null,
    primary key (id)
) COLLATE='utf8_general_ci'
engine = innodb;

create table test.menu_group
(
    id   bigint not null auto_increment,
    name varchar(255) not null,
    primary key (id)
) COLLATE='utf8_general_ci'
engine = innodb;

create table test.menu_product
(
    seq        bigint not null auto_increment,
    quantity   bigint not null,
    product_id bigint not null,
    menu_id    bigint not null,
    primary key (seq)
) COLLATE='utf8_general_ci'
engine = innodb;

create table test.product
(
    id    bigint not null,
    name  varchar(255)   not null,
    price int(10) not null,
    primary key (id)
) COLLATE='utf8_general_ci'
engine = innodb;

create table test.order_line_item
(
    seq      bigint not null auto_increment,
    quantity bigint not null,
    menu_id  bigint not null,
    order_id bigint not null,
    primary key (seq)
) COLLATE='utf8_general_ci'
engine = innodb;

create table test.order_table
(
    id               bigint not null auto_increment,
    occupied         bit          not null,
    name             varchar(255) not null,
    number_of_guests integer      not null,
    primary key (id)
) COLLATE='utf8_general_ci'
engine = innodb;

create table test.orders
(
    id               bigint not null,
    delivery_address varchar(255),
    order_date_time  datetime not null,
    status           varchar(255) not null,
    type             varchar(255) not null,
    order_table_id   bigint,
    primary key (id)
) COLLATE='utf8_general_ci'
engine = innodb;



alter table test.menu
    add constraint fk_menu_to_menu_group
        foreign key (menu_group_id)
            references test.menu_group (id);

alter table test.menu_product
    add constraint fk_menu_product_to_product
        foreign key (product_id)
            references test.product (id);

alter table test.menu_product
    add constraint fk_menu_product_to_menu
        foreign key (menu_id)
            references test.menu (id);

alter table test.order_line_item
    add constraint fk_order_line_item_to_menu
        foreign key (menu_id)
            references test.menu (id);

alter table test.order_line_item
    add constraint fk_order_line_item_to_orders
        foreign key (order_id)
            references test.orders (id);

alter table test.orders
    add constraint fk_orders_to_order_table
        foreign key (order_table_id)
            references test.order_table (id);
