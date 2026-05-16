# GPU登记系统表关系与角色权限说明（2026-05-17）

## 1. 核心表与职责
- `sys_user`
  - 系统登录账号主表。
  - 关键字段：`id`（登录身份主键）、`username`、`realname`、`work_no`、`phone`、`email`。
- `lab_user`
  - 实验室业务成员档案。
  - 关键字段：`id`（业务成员主键）、`username`（成员姓名）、`person_type`、`group_id`、`role_code`。
- `lab_user_bind`
  - 身份桥接表：`lab_user.id -> sys_user.id`。
  - 用于打通“登录身份”和“业务成员身份”。
- `lab_group`
  - 课题组信息，`group_id` 为组主键。
- `lab_project`
  - 项目主表，绑定 `group_id`。
- `lab_equipment`
  - GPU资源主表，绑定 `group_id`。
- `lab_application`
  - 用户申请表（组员填写，组长审批）。
  - 关键约定：`user_id` 统一存 `sys_user.id`。
- `lab_use_record`
  - 实际使用记录表。
  - 关键约定：`user_id` 统一存 `sys_user.id`。

## 2. 依赖关系
- 身份依赖：`lab_user_bind.lab_user_id -> lab_user.id`，`lab_user_bind.sys_user_id -> sys_user.id`。
- 组织依赖：`lab_user.group_id`、`lab_project.group_id`、`lab_equipment.group_id` 同口径。
- 业务依赖：
  - `lab_application.user_id -> sys_user.id`
  - `lab_use_record.user_id -> sys_user.id`
  - `lab_application.project_id -> lab_project.id`
  - `lab_application.equipment_id -> lab_equipment.id`

## 3. 角色与权限边界
- 超级管理员（`role_code=super_admin` 或系统 `admin` 账号）
  - 可增删改查：组长、组员、项目、GPU资源、申请记录。
  - 可跨组查看全量数据。
- 组长（`role_code=admin` 或 `person_type` 命中组长口径）
  - 可增删改查：本组组员。
  - 可维护：本组项目、本组GPU资源。
  - 可查看/审批：本组成员申请。
- 普通组员（`role_code=user`）
  - 可填写申请。
  - 仅可查看本人申请和本人记录。

## 4. 为什么会出现“导出有数据但前端无显示”
- 根因是 `user_id` 混用了两套主键：
  - 一部分记录写入 `lab_user.id`
  - 一部分记录写入 `sys_user.id`
- 前端/后端过滤口径按 `sys_user.id` 时，写成 `lab_user.id` 的数据会被过滤掉。

## 5. 当前修复策略
- 后端权限上下文统一：优先按 `sys_user.id` 过滤，兼容历史 `lab_user.id`。
- 申请相关页面字典统一：`userId` 使用 `sys_user` 字典回显。
- 数据迁移脚本统一：将 `lab_application.user_id` / `lab_use_record.user_id` 归一到 `sys_user.id`。
- 通过 `lab_user_bind` 维护成员与登录账号映射，避免再次混用。

## 6. 执行与验证
- 执行脚本：`jeecg-boot/db/lab_role_data_alignment_20260517.sql`
- 验证重点：
  - `lab_application.user_id` 是否全部能在 `sys_user.id` 找到。
  - 组长登录后是否能看到本组申请。
  - 组员提交申请后，是否立即出现在“我的申请”列表。
