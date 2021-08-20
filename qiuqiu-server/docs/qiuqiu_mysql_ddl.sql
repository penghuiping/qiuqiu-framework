## 审计日志表
drop table if exists t_audit_log;
create table t_audit_log
(
    id          bigint primary key auto_increment comment 'id主键',
    uri         varchar(255) comment '访问的接口资源',
    params      varchar(255) comment '请求参数',
    create_time datetime comment '创建日期',
    username    varchar(255) comment '用户名'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '审计日志表';

## 数据字典表
drop table if exists t_dict;
create table t_dict
(
    id          bigint primary key auto_increment comment 'id主键',
    key0       varchar(255) unique not null comment '键',
    value       varchar(255) comment '值',
    description varchar(255) comment '描述',
    enable      tinyint             not null comment '0: 无效,1:有效,2:软删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '数据字典表';

## 用户组表
drop table if exists t_group;
create table t_group
(
    id          bigint primary key auto_increment comment 'id主键',
    name        varchar(255) not null comment '组名',
    description varchar(255) not null comment '组描述',
    parent_id   bigint comment '父id',
    enable      tinyint      not null comment '0: 无效,1:有效,2:软删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '用户组表';

## 资源表
drop table if exists t_resource;
create table t_resource
(
    name        varchar(32) primary key comment '资源名',
    description varchar(255) comment '资源描述',
    enable      tinyint not null comment '0: 无效,1:有效,2:软删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '资源表';

## 权限表
drop table if exists t_permission;
create table t_permission
(
    name        varchar(32) primary key comment '权限名',
    description varchar(255) comment '权限描述',
    enable      tinyint not null comment '0: 无效,1:有效,2:软删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '权限表';

## 资源权限映射表
drop table if exists t_resource_permission;
create table t_resource_permission
(
    resource   varchar(32) comment '资源名',
    permission varchar(32) comment '权限名'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '资源权限映射表';

## 角色表
drop table if exists t_role;
create table t_role
(
    id          bigint primary key auto_increment comment 'id主键',
    name        varchar(32) comment '角色名',
    description varchar(255) comment '角色描述',
    enable      tinyint not null comment '0: 无效,1:有效,2:软删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '角色表';

## 角色资源权限映射表
drop table if exists t_role_resource_permission;
create table t_role_resource_permission
(
    role_id    bigint comment '角色id',
    resource   varchar(32) comment '资源名',
    permission varchar(32) comment '权限名'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '角色资源权限映射表';

## 定时任务表
drop table if exists t_timer_job;
create table t_timer_job
(
    id          varchar(50) primary key comment '定时任务id',
    class_name  varchar(255) comment '对应的java执行代码类',
    name        varchar(50) comment '任务名',
    description varchar(255) comment '任务描述',
    group_id    bigint comment '用户组id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '定时任务表';

## 定时任务执行表
drop table if exists t_timer_job_execution;
create table t_timer_job_execution
(
    id                  varchar(50) primary key comment '定时任务执行id',
    cron                varchar(255) comment 'cron表达式',
    group_id            bigint comment '用户组id',
    job_id              varchar(50) comment '任务id',
    job_name            varchar(50) comment '任务名',
    params              varchar(255) comment '任务入参',
    enable              tinyint not null comment '0: 无效,1:有效,2:软删除',
    timer_loaded_number int comment '有多少定时器加载了此执行任务'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '定时任务执行表';

## 定时任务执行日志表
drop table if exists t_timer_job_log;
create table t_timer_job_log
(
    id             bigint primary key auto_increment comment '定时任务执行日志id',
    job_name       varchar(255) comment '定时任务名',
    execute_time   datetime comment '定时任务执行时间',
    result_code    int comment '执行结果码',
    result_message varchar(255) comment '执行结果码对应描述',
    job_id         varchar(255) comment '定时任务id',
    group_id       bigint comment '用户组id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '定时任务执行日志表';

## 定时器内部日志表
drop table if exists t_timer_inner_log;
create table t_timer_inner_log
(
    id             varchar(50) comment '定时任务id',
    execution_time bigint comment '执行时间',
    status         int comment '0:未执行,1:已执行'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '定时器内部日志表';

alter table t_timer_inner_log
    add constraint t_timer_inner_log_pk
        primary key (id, execution_time);

## 用户表
drop table if exists t_user;
create table t_user
(
    id                 bigint primary key auto_increment comment '用户id主键',
    nickname           varchar(255) comment '用户昵称',
    username           varchar(50) comment '用户名',
    password           varchar(255) comment '密码',
    create_time        datetime comment '创建时间',
    last_modified_time datetime comment '最后修改时间',
    data_access_level  varchar(50) comment '数据访问级别',
    group_id           bigint comment '用户组id',
    enable             tinyint not null comment '0: 无效,1:有效,2:软删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '用户表';

## 用户角色映射表
drop table if exists t_user_role;
create table t_user_role
(
    user_id bigint comment '用户id',
    role_id bigint comment '角色id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT '用户角色映射表';
