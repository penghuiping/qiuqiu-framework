create table t_audit_log
(
    id bigint primary key auto_increment,
    uri varchar(255),
    params varchar(255),
    create_time datetime,
    username varchar(255)
);

create table t_dict
(
    id bigint primary key auto_increment,
    key0  varchar(255) unique not null,
    value varchar(255),
    description varchar(255),
    enable int
);

create table t_group
(
    id bigint primary key  auto_increment,
    name varchar(255),
    description varchar(255),
    parent_id bigint,
    enable int
);

create table t_resource
(
    name varchar(32) primary key,
    description varchar(255),
    enable int
);

create table t_permission
(
    name varchar(32) primary key,
    description varchar(255),
    enable int
);

create table t_resource_permission
(
    resource varchar(32),
    permission varchar(32)
);


create table t_role
(
    id bigint primary key auto_increment,
    name varchar(32),
    description varchar(255),
    enable int
);

create table t_role_resource_permission
(
    role_id bigint,
    resource varchar(32),
    permission varchar(32)
);

create table t_timer_job
(
    id varchar(50) primary key,
    class_name varchar(255),
    name varchar(50),
    description varchar(255),
    group_id bigint
);

create table t_timer_job_execution
(
    id varchar(50) primary key,
    cron varchar(255),
    group_id bigint,
    job_id varchar(50),
    job_name varchar(50),
    enable int,
    timer_loaded_number int
);

create table t_timer_job_log
(
    id bigint primary key  auto_increment,
    job_name varchar(255),
    execute_time datetime,
    result_code int,
    result_message varchar(255),
    job_id varchar(255),
    group_id bigint
);

create table t_timer_inner_log
(
    id varchar(50) primary key,
    execution_time datetime,
    status int
);

create table t_user
(
    id bigint primary key auto_increment,
    nickname varchar(255),
    username varchar(50),
    password varchar(255),
    create_time datetime,
    last_modified_time datetime,
    group_id bigint,
    enable int
);

create table t_user_role
(
    user_id bigint,
    role_id bigint
);
