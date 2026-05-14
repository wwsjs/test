-- 注意：该页面对应的前台目录为views/application文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177875108220101', NULL, '该表存储申请信息', '/application/labApplicationList', 'application/LabApplicationList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875108220102', '177875108220101', '添加该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'application:lab_application:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875108220103', '177875108220101', '编辑该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'application:lab_application:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875108220104', '177875108220101', '删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'application:lab_application:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875108220105', '177875108220101', '批量删除该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'application:lab_application:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875108220106', '177875108220101', '导出excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'application:lab_application:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177875108220107', '177875108220101', '导入excel_该表存储申请信息', NULL, NULL, 0, NULL, NULL, 2, 'application:lab_application:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-14 17:31:22', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220108', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220101', NULL, '2026-05-14 17:31:22', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220109', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220102', NULL, '2026-05-14 17:31:22', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220210', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220103', NULL, '2026-05-14 17:31:22', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220211', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220104', NULL, '2026-05-14 17:31:22', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220212', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220105', NULL, '2026-05-14 17:31:22', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220213', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220106', NULL, '2026-05-14 17:31:22', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177875108220214', 'f6817f48af4fb3af11b9e8bf182f618b', '177875108220107', NULL, '2026-05-14 17:31:22', '127.0.0.1');