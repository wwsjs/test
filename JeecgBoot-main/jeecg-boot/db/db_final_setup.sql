-- ============================================
-- 仪器设备使用记录系统优化版数据库
-- 包含表: lab_user, lab_equipment, lab_project, lab_use_record
-- 修正版 - 增加用户姓名字段
-- ============================================

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- 1. 删除已存在的表
-- ============================================
DROP TABLE IF EXISTS lab_use_record;
DROP TABLE IF EXISTS lab_project;
DROP TABLE IF EXISTS lab_equipment;
DROP TABLE IF EXISTS lab_user;

-- ============================================
-- 1. 用户表
-- ============================================
CREATE TABLE lab_user (
  id VARCHAR(32) PRIMARY KEY,
  work_no VARCHAR(64) NOT NULL COMMENT '工号/学号',
  username VARCHAR(64) NOT NULL COMMENT '姓名',
  phone VARCHAR(32) COMMENT '联系电话',
  email VARCHAR(128) COMMENT '邮箱',
  room_no VARCHAR(64) COMMENT '办公室/实验室房间号',
  person_type ENUM('teacher','undergraduate','master','phd') NOT NULL COMMENT '人员类别',
  grade VARCHAR(32) COMMENT '年级',
  tutor VARCHAR(32) COMMENT '导师',
  role ENUM('super_admin','admin','user') NOT NULL COMMENT '用户角色',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='用户管理表';

-- 示例数据
INSERT INTO lab_user(id, work_no, username, phone, email, room_no, person_type, grade, tutor, role)
VALUES
('u001','1001','张老师','13800000001','teacher1@example.com','A101','teacher',NULL,NULL,'super_admin'),
('u002','1002','李组长','13800000002','admin1@example.com','A102','teacher',NULL,NULL,'admin'),
('u003','2023001','王学生','13800000003','student1@example.com','B201','undergraduate','2023','u002','user'),
('u004','2023002','赵学生','13800000004','student2@example.com','B202','undergraduate','2023','u002','user');

-- ============================================
-- 2. 设备表
-- ============================================
CREATE TABLE lab_equipment (
  id VARCHAR(32) PRIMARY KEY,
  code VARCHAR(32) NOT NULL COMMENT '设备编号',
  name VARCHAR(128) NOT NULL COMMENT '设备名称',
  status ENUM('normal','fault') DEFAULT 'normal' COMMENT '设备状态',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='仪器设备表';

-- 示例数据
INSERT INTO lab_equipment(id, code, name, status)
VALUES
('e001','001','显卡001','normal'),
('e002','002','显卡002','normal');

-- ============================================
-- 3. 项目表
-- ============================================
CREATE TABLE lab_project (
  id VARCHAR(32) PRIMARY KEY,
  project_no VARCHAR(64) NOT NULL COMMENT '项目编号',
  project_name VARCHAR(128) NOT NULL COMMENT '项目名称',
  start_date DATE NOT NULL COMMENT '开始时间',
  end_date DATE NOT NULL COMMENT '结束时间',
  leader_id VARCHAR(32) NOT NULL COMMENT '项目负责人ID',
  leader_name VARCHAR(64) COMMENT '项目负责人姓名',
  contract_amount DECIMAL(12,2) DEFAULT 0 COMMENT '合同经费',
  member_ids TEXT COMMENT '项目成员ID列表(JSON)',
  member_names TEXT COMMENT '项目成员姓名列表(JSON)',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='项目信息表';

-- 示例数据
INSERT INTO lab_project(id, project_no, project_name, start_date, end_date, leader_id, leader_name, contract_amount, member_ids, member_names)
VALUES
('p001','P001','AI训练项目','2026-05-01','2026-12-31','u002','李组长','50000','["u003","u004"]','["王学生","赵学生"]'),
('p002','P002','显卡测试项目','2026-05-05','2026-08-30','u002','李组长','30000','["u003"]','["王学生"]');

-- ============================================
-- 4. 使用记录表（优化版，增加姓名字段）
-- ============================================
CREATE TABLE lab_use_record (
  id VARCHAR(32) PRIMARY KEY,
  user_id VARCHAR(32) NOT NULL COMMENT '使用人ID',
  username VARCHAR(64) NOT NULL COMMENT '使用人姓名',
  equipment_id VARCHAR(32) NOT NULL COMMENT '设备ID',
  equipment_name VARCHAR(128) COMMENT '设备名称',
  project_id VARCHAR(32) COMMENT '依托项目ID',
  project_name VARCHAR(128) COMMENT '项目名称',
  start_date DATE NOT NULL COMMENT '开始日期',
  end_date DATE NOT NULL COMMENT '结束日期',
  purpose VARCHAR(500) COMMENT '用途或项目内容',
  actual_hours DECIMAL(5,2) NOT NULL COMMENT '实际使用时间/小时',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='设备使用记录表';

-- 创建触发器：在插入或更新时自动同步相关名称
DELIMITER //
CREATE TRIGGER trg_sync_use_record_names_on_insert
BEFORE INSERT ON lab_use_record
FOR EACH ROW
BEGIN
  -- 如果提供了user_id，自动查询并填充username
  IF NEW.user_id IS NOT NULL AND NEW.username IS NULL THEN
    SET NEW.username = (SELECT username FROM lab_user WHERE id = NEW.user_id);
  END IF;
  
  -- 如果提供了equipment_id，自动查询并填充equipment_name
  IF NEW.equipment_id IS NOT NULL AND NEW.equipment_name IS NULL THEN
    SET NEW.equipment_name = (SELECT name FROM lab_equipment WHERE id = NEW.equipment_id);
  END IF;
  
  -- 如果提供了project_id，自动查询并填充project_name
  IF NEW.project_id IS NOT NULL AND NEW.project_name IS NULL THEN
    SET NEW.project_name = (SELECT project_name FROM lab_project WHERE id = NEW.project_id);
  END IF;
END//

CREATE TRIGGER trg_sync_use_record_names_on_update
BEFORE UPDATE ON lab_use_record
FOR EACH ROW
BEGIN
  -- 如果user_id有变化，自动更新username
  IF NEW.user_id IS NOT NULL AND (OLD.user_id IS NULL OR NEW.user_id != OLD.user_id) THEN
    SET NEW.username = (SELECT username FROM lab_user WHERE id = NEW.user_id);
  END IF;
  
  -- 如果equipment_id有变化，自动更新equipment_name
  IF NEW.equipment_id IS NOT NULL AND (OLD.equipment_id IS NULL OR NEW.equipment_id != OLD.equipment_id) THEN
    SET NEW.equipment_name = (SELECT name FROM lab_equipment WHERE id = NEW.equipment_id);
  END IF;
  
  -- 如果project_id有变化，自动更新project_name
  IF NEW.project_id IS NOT NULL AND (OLD.project_id IS NULL OR NEW.project_id != OLD.project_id) THEN
    SET NEW.project_name = (SELECT project_name FROM lab_project WHERE id = NEW.project_id);
  END IF;
END//
DELIMITER ;

-- 示例数据
INSERT INTO lab_use_record(id, user_id, equipment_id, project_id, start_date, end_date, purpose, actual_hours)
VALUES
('ur001','u003','e001','p001','2026-05-05','2026-05-05','AI模型训练',5.5),
('ur002','u004','e002','p001','2026-05-05','2026-05-06','模型测试',6.0),
('ur003','u003','e002','p002','2026-05-06','2026-05-06','显卡性能测试',4.0);

-- 重新启用外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 验证数据插入
-- ============================================
SELECT '用户表数据:' AS 表名, COUNT(*) AS 记录数 FROM lab_user
UNION ALL
SELECT '设备表数据:', COUNT(*) FROM lab_equipment
UNION ALL
SELECT '项目表数据:', COUNT(*) FROM lab_project
UNION ALL
SELECT '使用记录表数据:', COUNT(*) FROM lab_use_record;

-- 查看使用记录，验证姓名字段是否自动填充
SELECT 
  ur.id,
  ur.user_id,
  ur.username,
  ur.equipment_id,
  ur.equipment_name,
  ur.project_id,
  ur.project_name,
  ur.start_date,
  ur.end_date,
  ur.purpose,
  ur.actual_hours
FROM lab_use_record ur;