create table t_group
(
    id          integer
        constraint t_group_pk
        primary key autoincrement,
    name        varchar,
    description varchar,
    parent_id   int,
    enable      int
);

create table t_permission
(
    id          integer
        constraint t_permission_pk
        primary key autoincrement,
    name        varchar,
    description varchar,
    uri         varchar,
    enable      int
);

create table t_role
(
    id          integer
        constraint t_role_pk
        primary key autoincrement,
    name        varchar,
    description varchar,
    enable      int
);

create table t_role_permission
(
    role_id       integer,
    permission_id integer
);

create table t_user
(
    id                 integer
        constraint t_user_pk
        primary key autoincrement,
    nickname           varchar,
    username           varchar,
    password           varchar,
    create_time        datetime,
    last_modified_time datetime,
    group_id           int,
    enable             int
);

create table t_user_role
(
    user_id integer,
    role_id integer
);

