-- 注意：该页面对应的前台目录为views/lab_application文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177875599431001', NULL, '该表存储申请信息', '/lab_application/labApplicationList', 'lab_application/LabApplicationList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875599431102', '177875599431001', '添加该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab_application:lab_application:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875599431103', '177875599431001', '编辑该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab_application:lab_application:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875599431104', '177875599431001', '删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab_application:lab_application:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875599431105', '177875599431001', '批量删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab_application:lab_application:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875599431106', '177875599431001', '导出excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab_application:lab_application:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875599431107', '177875599431001', '导入excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'lab_application:lab_application:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 18:53:14', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431108', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431001', NULL, '2026-05-14 18:53:14', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431109', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431102', NULL, '2026-05-14 18:53:14', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431110', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431103', NULL, '2026-05-14 18:53:14', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431111', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431104', NULL, '2026-05-14 18:53:14', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431112', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431105', NULL, '2026-05-14 18:53:14', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431113', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431106', NULL, '2026-05-14 18:53:14', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875599431114', 'f6817f48af4fb3af11b9e8bf182f618b', '177875599431107', NULL, '2026-05-14 18:53:14', '127.0.0.1');