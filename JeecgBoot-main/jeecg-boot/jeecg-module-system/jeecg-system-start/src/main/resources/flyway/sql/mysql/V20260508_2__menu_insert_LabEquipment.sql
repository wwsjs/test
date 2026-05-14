-- 注意：该页面对应的前台目录为views/lab文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177822109916701', NULL, '实验室设备信息', '/lab/labEquipmentList', 'lab/LabEquipmentList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822109916702', '177822109916701', '添加实验室设备信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_equipment:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822109916703', '177822109916701', '编辑实验室设备信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_equipment:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822109916704', '177822109916701', '删除实验室设备信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_equipment:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822109916705', '177822109916701', '批量删除实验室设备信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_equipment:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822109916706', '177822109916701', '导出excel_实验室设备信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_equipment:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822109916707', '177822109916701', '导入excel_实验室设备信息', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_equipment:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 14:18:19', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916708', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916701', NULL, '2026-05-08 14:18:19', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916709', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916702', NULL, '2026-05-08 14:18:19', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916710', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916703', NULL, '2026-05-08 14:18:19', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916711', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916704', NULL, '2026-05-08 14:18:19', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916712', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916705', NULL, '2026-05-08 14:18:19', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916913', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916706', NULL, '2026-05-08 14:18:19', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822109916914', 'f6817f48af4fb3af11b9e8bf182f618b', '177822109916707', NULL, '2026-05-08 14:18:19', '127.0.0.1');