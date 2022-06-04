create table category
(
    id          int auto_increment
        primary key,
    name        varchar(100)  null,
    description varchar(2000) null,
    age_group   varchar(20)   null,
    created     datetime      null,
    inserted    bigint        null
);


create table if not exists lego_set
(
    id      integer auto_increment
        primary key,
    name    varchar(100),
    min_age integer,
    max_age integer
);
create table if not exists handbuch
(
    handbuch_id integer auto_increment
        primary key,
    author      varchar(100),
    text        longblob
);
create table if not exists model
(
    name        varchar(100),
    description longblob,
    lego_set    integer
);
