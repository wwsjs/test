-- 注意：该页面对应的前台目录为views/lab文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177822724348501', NULL, '该表存储申请信息', '/lab/labApplicationManageList', 'lab/LabApplicationManageList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822724348502', '177822724348501', '添加该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822724348503', '177822724348501', '编辑该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822724348504', '177822724348501', '删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822724348505', '177822724348501', '批量删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822724348506', '177822724348501', '导出excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822724348507', '177822724348501', '导入excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_application:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 16:00:43', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349308', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348501', NULL, '2026-05-08 16:00:43', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349309', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348502', NULL, '2026-05-08 16:00:43', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349310', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348503', NULL, '2026-05-08 16:00:43', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349311', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348504', NULL, '2026-05-08 16:00:43', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349312', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348505', NULL, '2026-05-08 16:00:43', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349313', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348506', NULL, '2026-05-08 16:00:43', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822724349314', 'f6817f48af4fb3af11b9e8bf182f618b', '177822724348507', NULL, '2026-05-08 16:00:43', '127.0.0.1');