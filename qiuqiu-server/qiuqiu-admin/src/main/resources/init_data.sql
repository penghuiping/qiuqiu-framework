INSERT INTO t_user (id, nickname, username, password, create_time, last_modified_time, group_id, enable) VALUES (1, '超级管理员', 'admin', '123456', null, null, 1, 1);
INSERT INTO t_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO t_role (id, name, description, enable) VALUES (1, 'admin', '超级管理员', 1);

INSERT INTO t_permission (id, name, description, uri, enable) VALUES (1, 'user_info', '获取当前登入用户信息', '/user/info', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (2, 'user_detail', '获取用户详情', '/user/detail', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (3, 'user_page', '用户列表分页查询', '/user/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (4, 'user_create', '新增用户 ', '/user/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (5, 'user_update', '更新用户', '/user/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (6, 'user_delete', '删除用户', '/user/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (7, 'user_logout', '用户登出', '/user/logout', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (8, 'role_get_all', '获取系统所有角色', '/role/getAll', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (9, 'role_get_all', '获取系统所有组', '/group/getAll', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (10, 'role_page', '角色列表分页查询', '/role/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (11, 'role_create', '新增角色', '/role/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (12, 'role_update', '更新角色', '/role/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (13, 'role_delete', '删除角色', '/role/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (14, 'role_detail', '角色详情', '/role/detail', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (15, 'permission_get_all', '获取系统所有权限', '/permission/getAll', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (16, 'permission_page', '权限列表分页查询', '/permission/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (17, 'permission_create', '新增权限', '/permission/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (18, 'permission_update', '更新权限', '/permission/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (19, 'permission_delete', '删除权限', '/permission/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (21, 'group_page', '用户组列表分页查询', '/group/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (22, 'group_create', '新增用户组', '/group/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (23, 'group_update', '更新用户组', '/group/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (24, 'group_delete', '删除用户组', '/group/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (25, 'audit_log_page', '审计日志分页查询', '/audit_log/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (26, 'dict_page', '数据字典分页查询', '/dict/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (27, 'dict_create', '新增数据字典', '/dict/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (28, 'dict_update', '更新数据字典', '/dict/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (29, 'dict_delete', '删除数据字典记录', '/dict/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (30, 'dict_refresh', '刷新数据字典缓存', '/dict/refresh', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (31, 'job_page', '定时任务分页查询', '/job/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (32, 'job_create', '新增定时任务', '/job/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (33, 'job_update', '更新定时任务', '/job/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (34, 'job_delete', '删除定时任务', '/job/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (35, 'job_execution_refresh', '任务执行计划刷新', '/job/execution/refresh', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (36, 'job_execution_refresh_all', '任务执行计划全部刷新', '/job/execution/refresh_all', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (37, 'job_log_page', '任务日志分页查询', '/job/log/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (38, 'job_execution_page', '任务执行计划分页查询', '/job/execution/page', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (39, 'job_execution_create', '新增任务执行计划', '/job/execution/create', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (40, 'job_execution_update', '更新任务执行计划', '/job/execution/update', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (41, 'job_execution_delete', '删除任务执行计划', '/job/execution/delete', 1);
INSERT INTO t_permission (id, name, description, uri, enable) VALUES (42, 'job_find_all', '获取系统中所有可执行计划', '/job/find_all', 1);



INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 3);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 4);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 7);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 8);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 9);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 10);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 11);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 12);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 13);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 14);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 15);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 16);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 17);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 18);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 19);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 21);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 22);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 23);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 24);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 25);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 26);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 27);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 28);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 30);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 31);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 32);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 33);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 34);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 35);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 36);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 37);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 38);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 39);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 40);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 41);
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 42);


INSERT INTO t_dict (id, key0, value, description, enable) VALUES (1, 'test', 'test_value', '这是用于测试', 1);
INSERT INTO t_dict (id, key0, value, description, enable) VALUES (3, 'sys_notify_enable', 'true', '系统通知', 0);
INSERT INTO t_dict (id, key0, value, description, enable) VALUES (4, '127.0.0.1:9091', 'http://127.0.0.1:9091/actuator/prometheus', '本机状态监控信息', 1);
INSERT INTO t_dict (id, key0, value, description, enable) VALUES (5, 'apps_ip_port', '["127.0.0.1:9091"]', '应用服务器监控端口列表', 1);


INSERT INTO t_group (id, name, description, parent_id, enable) VALUES (1, 'root', '总部', null, 1);
INSERT INTO t_group (id, name, description, parent_id, enable) VALUES (2, 'sh_1', '上海分部1', 1, 1);
INSERT INTO t_group (id, name, description, parent_id, enable) VALUES (3, 'sh_2', '上海分部2', 1, 1);
INSERT INTO t_group (id, name, description, parent_id, enable) VALUES (4, 'mh_1', '闵行分部1', 2, 1);
INSERT INTO t_group (id, name, description, parent_id, enable) VALUES (5, 'mh_2', '闵行分部2', 2, 1);


INSERT INTO t_timer_job (id, class_name, name, description, group_id) VALUES ('4d7f14ed-bdf7-4bde-b5a3-54bb1986fa36', 'com.php25.qiuqiu.admin.job.SystemMonitorInfoPrintJob', 'per_minute_sys_monitor', '每分钟系统监控', 1);
INSERT INTO t_timer_job (id, class_name, name, description, group_id) VALUES ('c0408b9d-cb6f-49e9-8bb2-93ed033d4267', 'com.php25.qiuqiu.admin.job.SystemNotifyJob', 'sys_notify', '系统通知', 1);
INSERT INTO t_timer_job (id, class_name, name, description, group_id) VALUES ('d8d0c2e7-e93b-46ef-88b2-8e2d52782185', 'com.php25.qiuqiu.admin.job.NotifyTextJob', 'common_text_notify', '普通文本通知', 4);

