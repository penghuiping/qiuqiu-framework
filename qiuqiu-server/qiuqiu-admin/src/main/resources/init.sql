create table t_audit_log
(
    id INTEGER
        constraint t_audit_log_pk
        primary key autoincrement,
    uri varchar,
    params varchar,
    create_time datetime,
    username varchar
);

create table t_dict
(
    id integer
        constraint t_dict_pk
        primary key autoincrement,
    key0 varchar,
    value varchar,
    description varchar,
    enable int
);

create unique index t_dict_key_uindex
    on t_dict (key0);

create table t_group
(
    id integer
        constraint t_group_pk
        primary key autoincrement,
    name varchar,
    description varchar,
    parent_id int,
    enable int
);

create table t_permission
(
    id integer
        constraint t_permission_pk
        primary key autoincrement,
    name varchar,
    description varchar,
    uri varchar,
    enable int
);

create table t_role
(
    id integer
        constraint t_role_pk
        primary key autoincrement,
    name varchar,
    description varchar,
    enable int
);

create table t_role_permission
(
    role_id integer,
    permission_id integer
);

create table t_timer_job
(
    id varchar
        constraint t_timer_job_pk
        primary key,
    class_name varchar,
    name varchar,
    description varchar,
    group_id int
);

create table t_timer_job_execution
(
    id varchar
        constraint t_timer_job_execution_pk
        primary key,
    cron varchar,
    group_id int,
    job_id varchar,
    job_name varchar,
    enable int,
    timer_loaded_number int
);

create table t_timer_job_log
(
    id integer
        constraint t_timer_jog_log_pk
        primary key autoincrement,
    job_name varchar,
    execute_time datetime,
    result_code int,
    result_message varchar,
    job_id varchar,
    group_id int
);

create table t_timer_inner_log
(
    id varchar,
    execution_time datetime,
    status int
);

create table t_user
(
    id integer
        constraint t_user_pk
        primary key autoincrement,
    nickname varchar,
    username varchar,
    password varchar,
    create_time datetime,
    last_modified_time datetime,
    group_id int,
    enable int
);

create table t_user_role
(
    user_id integer,
    role_id integer
);

