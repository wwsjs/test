-- 注意：该页面对应的前台目录为views/lab文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177868443778601', NULL, '课题组信息', '/lab/labGroupList', 'lab/LabGroupList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868443778602', '177868443778601', '添加课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868443778603', '177868443778601', '编辑课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868443778604', '177868443778601', '删除课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868443778605', '177868443778601', '批量删除课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868443778606', '177868443778601', '导出excel_课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177868443778607', '177868443778601', '导入excel_课题组信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_group:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-13 23:00:37', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778708', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778601', NULL, '2026-05-13 23:00:37', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778709', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778602', NULL, '2026-05-13 23:00:37', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778710', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778603', NULL, '2026-05-13 23:00:37', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778711', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778604', NULL, '2026-05-13 23:00:37', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778712', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778605', NULL, '2026-05-13 23:00:37', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778713', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778606', NULL, '2026-05-13 23:00:37', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177868443778714', 'f6817f48af4fb3af11b9e8bf182f618b', '177868443778607', NULL, '2026-05-13 23:00:37', '127.0.0.1');