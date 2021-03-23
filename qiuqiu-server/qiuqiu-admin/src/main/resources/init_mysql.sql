create table t_audit_log
(
    id bigint auto_increment
        primary key,
    uri varchar(255) null,
    params varchar(255) null,
    create_time datetime null,
    username varchar(255) null
);

create table t_dict
(
    id bigint auto_increment
        primary key,
    key0 varchar(255) null,
    value varchar(255) null,
    description varchar(255) null,
    enable int null,
    constraint t_dict_key_uindex
        unique (key0)
);

create table t_group
(
    id bigint auto_increment
        primary key,
    name varchar(255) null,
    description varchar(255) null,
    parent_id bigint null,
    enable int null
);

create table t_permission
(
    id bigint auto_increment
        primary key,
    name varchar(255) null,
    description varchar(255) null,
    uri varchar(255) null,
    enable int null
);

create table t_role
(
    id bigint auto_increment
        primary key,
    name varchar(255) null,
    description varchar(255) null,
    enable int null
);

create table t_role_permission
(
    role_id bigint null,
    permission_id bigint null
);

create table t_timer_inner_log
(
    id varchar(255) not null,
    execution_time bigint not null,
    status int null,
    primary key (id, execution_time)
);

create table t_timer_job
(
    id varchar(255) not null
        primary key,
    class_name varchar(255) null,
    name varchar(255) null,
    description varchar(255) null,
    group_id bigint null
);

create table t_timer_job_execution
(
    id varchar(255) not null
        primary key,
    cron varchar(255) null,
    group_id bigint null,
    job_id varchar(255) null,
    job_name varchar(255) null,
    enable int null,
    timer_loaded_number int null
);

create table t_timer_job_log
(
    id bigint auto_increment
        primary key,
    job_name varchar(255) null,
    execute_time datetime null,
    result_code int null,
    result_message varchar(255) null,
    job_id varchar(255) null,
    group_id int null
);

create table t_user
(
    id int auto_increment
        primary key,
    nickname varchar(255) null,
    username varchar(255) null,
    password varchar(255) null,
    create_time datetime null,
    last_modified_time datetime null,
    group_id bigint null,
    enable int null
);

create table t_user_role
(
    user_id bigint null,
    role_id bigint null
);

