-- 注意：该页面对应的前台目录为views/lab文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177868481429701', NULL, '课题组信息', '/lab/labGroupList', 'lab/LabGroupList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868481429802', '177868481429701', '添加课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868481429803', '177868481429701', '编辑课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868481429804', '177868481429701', '删除课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868481429805', '177868481429701', '批量删除课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868481429806', '177868481429701', '导出excel_课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868481429807', '177868481429701', '导入excel_课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:06:54', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429808', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429701', NULL, '2026-05-13 23:06:54', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429809', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429802', NULL, '2026-05-13 23:06:54', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429810', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429803', NULL, '2026-05-13 23:06:54', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429811', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429804', NULL, '2026-05-13 23:06:54', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429812', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429805', NULL, '2026-05-13 23:06:54', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429813', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429806', NULL, '2026-05-13 23:06:54', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868481429814', 'f6817f48af4fb3af11b9e8bf182f618b', '177868481429807', NULL, '2026-05-13 23:06:54', '127.0.0.1');