-- 注意：该页面对应的前台目录为views/lab文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177822113713701', NULL, '该表存储申请信息', '/lab/labApplicationList', 'lab/LabApplicationList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822113713702', '177822113713701', '添加该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822113713703', '177822113713701', '编辑该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822113713704', '177822113713701', '删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822113713705', '177822113713701', '批量删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822113713706', '177822113713701', '导出excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822113713707', '177822113713701', '导入excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:57', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713808', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713701', NULL, '2026-05-08 14:18:57', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713809', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713702', NULL, '2026-05-08 14:18:57', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713810', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713703', NULL, '2026-05-08 14:18:57', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713811', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713704', NULL, '2026-05-08 14:18:57', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713812', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713705', NULL, '2026-05-08 14:18:57', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713813', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713706', NULL, '2026-05-08 14:18:57', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822113713814', 'f6817f48af4fb3af11b9e8bf182f618b', '177822113713707', NULL, '2026-05-08 14:18:57', '127.0.0.1');