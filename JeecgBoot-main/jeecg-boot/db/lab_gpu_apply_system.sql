SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS lab_use_record;
DROP TABLE IF EXISTS lab_project_member;
DROP TABLE IF EXISTS lab_project;
DROP TABLE IF EXISTS lab_equipment;
DROP TABLE IF EXISTS lab_user;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- 1. 用户表
-- =========================
CREATE TABLE lab_user (
  id VARCHAR(32) PRIMARY KEY COMMENT '用户ID',
  work_no VARCHAR(64) NOT NULL COMMENT '工号/学号',
  username VARCHAR(64) NOT NULL COMMENT '姓名',
  phone VARCHAR(32) COMMENT '联系电话',
  email VARCHAR(128) COMMENT '邮箱',
  room_no VARCHAR(64) COMMENT '办公室/实验室房间号',
  person_type VARCHAR(32) NOT NULL COMMENT '人员类别：teacher/undergraduate/master/phd',
  grade VARCHAR(32) COMMENT '年级',
  tutor_id VARCHAR(32) COMMENT '导师ID',
  tutor_name VARCHAR(64) COMMENT '导师姓名',
  role_code VARCHAR(32) NOT NULL COMMENT '角色：super_admin/admin/user',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_lab_user_work_no (work_no),
  CONSTRAINT fk_lab_user_tutor FOREIGN KEY (tutor_id) REFERENCES lab_user(id)
) COMMENT='用户管理表';

INSERT INTO lab_user
(id, work_no, username, phone, email, room_no, person_type, grade, tutor_id, tutor_name, role_code)
VALUES
('u001','1001','张老师','13800000001','teacher1@example.com','A101','teacher',NULL,NULL,NULL,'super_admin'),
('u002','1002','李组长','13800000002','admin1@example.com','A102','teacher',NULL,NULL,NULL,'admin'),
('u003','2023001','王学生','13800000003','student1@example.com','B201','undergraduate','2023','u002','李组长','user'),
('u004','2023002','赵学生','13800000004','student2@example.com','B202','undergraduate','2023','u002','李组长','user');

-- =========================
-- 2. 设备表
-- =========================
CREATE TABLE lab_equipment (
  id VARCHAR(32) PRIMARY KEY COMMENT '设备ID',
  equipment_code VARCHAR(32) NOT NULL COMMENT '设备编号',
  equipment_name VARCHAR(128) NOT NULL COMMENT '设备名称',
  status VARCHAR(32) DEFAULT 'normal' COMMENT '设备状态：normal/fault',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_lab_equipment_code (equipment_code)
) COMMENT='仪器设备表';

INSERT INTO lab_equipment
(id, equipment_code, equipment_name, status)
VALUES
('e001','001','显卡001','normal'),
('e002','002','显卡002','normal');

-- =========================
-- 3. 项目表
-- =========================
CREATE TABLE lab_project (
  id VARCHAR(32) PRIMARY KEY COMMENT '项目ID',
  project_no VARCHAR(64) NOT NULL COMMENT '项目编号',
  project_name VARCHAR(128) NOT NULL COMMENT '项目名称',
  start_date DATE NOT NULL COMMENT '开始时间',
  end_date DATE NOT NULL COMMENT '结束时间',
  leader_id VARCHAR(32) NOT NULL COMMENT '项目负责人ID',
  leader_name VARCHAR(64) COMMENT '项目负责人姓名',
  contract_amount DECIMAL(12,2) DEFAULT 0 COMMENT '合同经费',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_lab_project_no (project_no),
  CONSTRAINT fk_lab_project_leader FOREIGN KEY (leader_id) REFERENCES lab_user(id)
) COMMENT='项目信息表';

INSERT INTO lab_project
(id, project_no, project_name, start_date, end_date, leader_id, leader_name, contract_amount)
VALUES
('p001','P001','AI训练项目','2026-05-01','2026-12-31','u002','李组长',50000.00),
('p002','P002','显卡测试项目','2026-05-05','2026-08-30','u002','李组长',30000.00);

-- =========================
-- 4. 项目成员表
-- =========================
CREATE TABLE lab_project_member (
  id VARCHAR(32) PRIMARY KEY COMMENT '项目成员ID',
  project_id VARCHAR(32) NOT NULL COMMENT '项目ID',
  user_id VARCHAR(32) NOT NULL COMMENT '成员用户ID',
  username VARCHAR(64) COMMENT '成员姓名',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_project_user (project_id, user_id),
  CONSTRAINT fk_lab_project_member_project FOREIGN KEY (project_id) REFERENCES lab_project(id),
  CONSTRAINT fk_lab_project_member_user FOREIGN KEY (user_id) REFERENCES lab_user(id)
) COMMENT='项目成员表';

INSERT INTO lab_project_member
(id, project_id, user_id, username)
VALUES
('pm001','p001','u003','王学生'),
('pm002','p001','u004','赵学生'),
('pm003','p002','u003','王学生');

-- =========================
-- 5. 使用记录表
-- =========================
CREATE TABLE lab_use_record (
  id VARCHAR(32) PRIMARY KEY COMMENT '使用记录ID',
  user_id VARCHAR(32) NOT NULL COMMENT '使用人ID',
  username VARCHAR(64) COMMENT '使用人姓名',
  equipment_id VARCHAR(32) NOT NULL COMMENT '设备ID',
  equipment_name VARCHAR(128) COMMENT '设备名称',
  project_id VARCHAR(32) NOT NULL COMMENT '依托项目ID',
  project_name VARCHAR(128) COMMENT '项目名称',
  start_date DATE NOT NULL COMMENT '开始日期',
  end_date DATE NOT NULL COMMENT '结束日期',
  purpose VARCHAR(500) COMMENT '用途或项目内容',
  actual_hours DECIMAL(8,2) NOT NULL COMMENT '实际使用时间/小时',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_lab_use_record_user FOREIGN KEY (user_id) REFERENCES lab_user(id),
  CONSTRAINT fk_lab_use_record_equipment FOREIGN KEY (equipment_id) REFERENCES lab_equipment(id),
  CONSTRAINT fk_lab_use_record_project FOREIGN KEY (project_id) REFERENCES lab_project(id)
) COMMENT='设备使用记录表';

INSERT INTO lab_use_record
(id, user_id, username, equipment_id, equipment_name, project_id, project_name, start_date, end_date, purpose, actual_hours)
VALUES
('ur001','u003','王学生','e001','显卡001','p001','AI训练项目','2026-05-05','2026-05-05','AI模型训练',5.50),
('ur002','u004','赵学生','e002','显卡002','p001','AI训练项目','2026-05-05','2026-05-06','模型测试',6.00),
('ur003','u003','王学生','e002','显卡002','p002','显卡测试项目','2026-05-06','2026-05-06','显卡性能测试',4.00);

SELECT 'lab_user' AS table_name, COUNT(*) AS count_num FROM lab_user
UNION ALL
SELECT 'lab_equipment', COUNT(*) FROM lab_equipment
UNION ALL
SELECT 'lab_project', COUNT(*) FROM lab_project
UNION ALL
SELECT 'lab_project_member', COUNT(*) FROM lab_project_member
UNION ALL
SELECT 'lab_use_record', COUNT(*) FROM lab_use_record;