-- 统一实验室模块 identity 映射与 user_id 口径（sys_user.id）

CREATE TABLE IF NOT EXISTS `lab_user_bind` (
  `lab_user_id` VARCHAR(64) NOT NULL,
  `sys_user_id` VARCHAR(64) NOT NULL,
  `bind_source` VARCHAR(20) NOT NULL DEFAULT 'auto',
  `bind_confidence` INT NOT NULL DEFAULT 100,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`lab_user_id`),
  KEY `idx_lab_user_bind_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实验室成员与系统账号映射表';

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'username', 100
FROM lab_user lu
JOIN sys_user su ON lu.username = su.username
WHERE lu.username IS NOT NULL AND lu.username <> ''
ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'realname', 95
FROM lab_user lu
JOIN sys_user su ON lu.username = su.realname
WHERE lu.username IS NOT NULL AND lu.username <> ''
ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'work_no', 95
FROM lab_user lu
JOIN sys_user su ON lu.work_no = su.work_no
WHERE lu.work_no IS NOT NULL AND lu.work_no <> ''
ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'phone', 90
FROM lab_user lu
JOIN sys_user su ON lu.phone = su.phone
WHERE lu.phone IS NOT NULL AND lu.phone <> ''
ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence);

INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence)
SELECT lu.id, su.id, 'email', 85
FROM lab_user lu
JOIN sys_user su ON lu.email = su.email
WHERE lu.email IS NOT NULL AND lu.email <> ''
ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence);

UPDATE lab_user
SET role_code = CASE
  WHEN LOWER(TRIM(person_type)) IN ('super_admin','superadmin','sa') THEN 'super_admin'
  WHEN LOWER(TRIM(person_type)) IN ('group_leader','leader','master','admin') THEN 'admin'
  ELSE 'user'
END
WHERE (role_code IS NULL OR role_code = '')
  AND person_type IS NOT NULL
  AND person_type <> '';

UPDATE lab_application a
JOIN lab_user_bind b ON a.user_id = b.lab_user_id
SET a.user_id = b.sys_user_id
WHERE a.user_id <> b.sys_user_id;

UPDATE lab_use_record r
JOIN lab_user_bind b ON r.user_id = b.lab_user_id
SET r.user_id = b.sys_user_id
WHERE r.user_id <> b.sys_user_id;
