-- 注意：该页面对应的前台目录为views/lab文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('177822613806801', NULL, '使用记录', '/lab/labUseRecordList', 'lab/LabUseRecordList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822613806802', '177822613806801', '添加使用记录', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_use_record:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822613806803', '177822613806801', '编辑使用记录', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_use_record:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822613806804', '177822613806801', '删除使用记录', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_use_record:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822613806805', '177822613806801', '批量删除使用记录', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_use_record:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822613806806', '177822613806801', '导出excel_使用记录', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_use_record:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('177822613806807', '177822613806801', '导入excel_使用记录', NULL, NULL, 0, NULL, NULL, 2, 'lab:lab_use_record:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2026-05-08 15:42:18', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806808', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806801', NULL, '2026-05-08 15:42:18', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806809', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806802', NULL, '2026-05-08 15:42:18', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806810', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806803', NULL, '2026-05-08 15:42:18', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806811', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806804', NULL, '2026-05-08 15:42:18', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806812', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806805', NULL, '2026-05-08 15:42:18', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806813', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806806', NULL, '2026-05-08 15:42:18', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('177822613806814', 'f6817f48af4fb3af11b9e8bf182f618b', '177822613806807', NULL, '2026-05-08 15:42:18', '127.0.0.1');