-- GPU 申请系统身份映射与角色口径统一脚本
-- 执行前请先 USE 目标库，例如: USE `jeecg-boot`;

-- 1) 兜底字段：申请表缺少 actual_hours 时补齐
SET @col_exists := (
  SELECT COUNT(1)
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'lab_application'
    AND COLUMN_NAME = 'actual_hours'
);
SET @ddl := IF(
  @col_exists = 0,
  'ALTER TABLE `lab_application` ADD COLUMN `actual_hours` DECIMAL(10,2) NULL COMMENT ''使用时间'' AFTER `purpose`',
  'SELECT 1'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2) 成员身份映射表：lab_user.id -> sys_user.id
CREATE TABLE IF NOT EXISTS `lab_user_bind` (
  `lab_user_id` VARCHAR(64) NOT NULL,
  `sys_user_id` VARCHAR(64) NOT NULL,
  `bind_source` VARCHAR(20) NOT NULL DEFAULT 'auto',
  `bind_confidence` INT NOT NULL DEFAULT 100,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`lab_user_id`),
  KEY `idx_lab_user_bind_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实验室成员与系统账号映射表';

-- 3) 自动建立映射（可重复执行）
INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'username', 100
FROM lab_user lu
JOIN sys_user su ON lu.username = su.username
WHERE lu.username IS NOT NULL AND lu.username <> ''
ON DUPLICATE KEY UPDATE
  sys_user_id = VALUES(sys_user_id),
  bind_source = VALUES(bind_source),
  bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'realname', 95
FROM lab_user lu
JOIN sys_user su ON lu.username = su.realname
WHERE lu.username IS NOT NULL AND lu.username <> ''
ON DUPLICATE KEY UPDATE
  sys_user_id = VALUES(sys_user_id),
  bind_source = VALUES(bind_source),
  bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'work_no', 95
FROM lab_user lu
JOIN sys_user su ON lu.work_no = su.work_no
WHERE lu.work_no IS NOT NULL AND lu.work_no <> ''
ON DUPLICATE KEY UPDATE
  sys_user_id = VALUES(sys_user_id),
  bind_source = VALUES(bind_source),
  bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'phone', 90
FROM lab_user lu
JOIN sys_user su ON lu.phone = su.phone
WHERE lu.phone IS NOT NULL AND lu.phone <> ''
ON DUPLICATE KEY UPDATE
  sys_user_id = VALUES(sys_user_id),
  bind_source = VALUES(bind_source),
  bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'email', 85
FROM lab_user lu
JOIN sys_user su ON lu.email = su.email
WHERE lu.email IS NOT NULL AND lu.email <> ''
ON DUPLICATE KEY UPDATE
  sys_user_id = VALUES(sys_user_id),
  bind_source = VALUES(bind_source),
  bind_confidence = VALUES(bind_confidence);

-- 4) 角色口径兜底：如果 role_code 为空，则按 person_type 推导
-- super_admin: 超级管理员
-- admin: 组长
-- user: 组员
UPDATE lab_user
SET role_code = CASE
  WHEN LOWER(TRIM(person_type)) IN ('super_admin','superadmin','sa') THEN 'super_admin'
  WHEN LOWER(TRIM(person_type)) IN ('group_leader','leader','master','admin') THEN 'admin'
  ELSE 'user'
END
WHERE (role_code IS NULL OR role_code = '')
  AND person_type IS NOT NULL
  AND person_type <> '';

-- 5) 统一申请表/使用记录表 user_id 为 sys_user.id
UPDATE lab_application a
JOIN lab_user_bind b ON a.user_id = b.lab_user_id
SET a.user_id = b.sys_user_id
WHERE a.user_id <> b.sys_user_id;

UPDATE lab_use_record r
JOIN lab_user_bind b ON r.user_id = b.lab_user_id
SET r.user_id = b.sys_user_id
WHERE r.user_id <> b.sys_user_id;

-- 6) 申请与使用记录互补：补齐空 user_id 和 actual_hours
UPDATE lab_use_record r
JOIN lab_application a
  ON a.equipment_id = r.equipment_id
 AND a.project_id = r.project_id
 AND a.start_date = r.start_date
 AND a.end_date = r.end_date
 AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '')
SET r.user_id = a.user_id
WHERE (r.user_id IS NULL OR r.user_id = '')
  AND a.user_id IS NOT NULL
  AND a.user_id <> '';

UPDATE lab_application a
JOIN lab_use_record r
  ON a.equipment_id = r.equipment_id
 AND a.project_id = r.project_id
 AND a.start_date = r.start_date
 AND a.end_date = r.end_date
 AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '')
SET a.user_id = r.user_id
WHERE (a.user_id IS NULL OR a.user_id = '')
  AND r.user_id IS NOT NULL
  AND r.user_id <> '';

UPDATE lab_application a
JOIN lab_use_record r
  ON a.equipment_id = r.equipment_id
 AND a.project_id = r.project_id
 AND a.start_date = r.start_date
 AND a.end_date = r.end_date
 AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '')
SET a.actual_hours = r.actual_hours
WHERE (a.actual_hours IS NULL OR a.actual_hours <= 0)
  AND r.actual_hours IS NOT NULL
  AND r.actual_hours > 0;

UPDATE lab_use_record r
JOIN lab_application a
  ON a.equipment_id = r.equipment_id
 AND a.project_id = r.project_id
 AND a.start_date = r.start_date
 AND a.end_date = r.end_date
 AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '')
SET r.actual_hours = a.actual_hours
WHERE (r.actual_hours IS NULL OR r.actual_hours <= 0)
  AND a.actual_hours IS NOT NULL
  AND a.actual_hours > 0;

-- 7) 项目成员兜底
UPDATE lab_project
SET member_id = leader_id
WHERE (member_id IS NULL OR member_id = '')
  AND leader_id IS NOT NULL
  AND leader_id <> '';

-- 8) 执行后核查 SQL（手动查看结果）
SELECT COUNT(*) AS app_user_not_in_sys_user
FROM lab_application a
LEFT JOIN sys_user su ON su.id = a.user_id
WHERE a.user_id IS NOT NULL AND a.user_id <> '' AND su.id IS NULL;

SELECT COUNT(*) AS use_record_user_not_in_sys_user
FROM lab_use_record r
LEFT JOIN sys_user su ON su.id = r.user_id
WHERE r.user_id IS NOT NULL AND r.user_id <> '' AND su.id IS NULL;

SELECT person_type, role_code, COUNT(*) AS cnt
FROM lab_user
GROUP BY person_type, role_code
ORDER BY person_type, role_code;
