-- 用户扩展信息表
CREATE TABLE lab_user_profile (
  id VARCHAR(32) PRIMARY KEY,
  user_id VARCHAR(32) NOT NULL COMMENT '关联sys_user.id',
  work_no VARCHAR(64) COMMENT '工号/学号',
  real_name VARCHAR(64) COMMENT '姓名',
  phone VARCHAR(32) COMMENT '联系电话',
  email VARCHAR(128) COMMENT '邮箱',
  room_no VARCHAR(64) COMMENT '办公室/实验室房间号',
  person_type VARCHAR(32) COMMENT '人员类别：teacher/undergraduate/master/phd',
  grade VARCHAR(32) COMMENT '年级',
  tutor_id VARCHAR(32) COMMENT '导师user_id',
  create_by VARCHAR(50),
  create_time DATETIME,
  update_by VARCHAR(50),
  update_time DATETIME,
  del_flag INT DEFAULT 0
) COMMENT='用户扩展信息';

-- 仪器设备表
CREATE TABLE lab_equipment (
  id VARCHAR(32) PRIMARY KEY,
  equipment_code VARCHAR(64) NOT NULL COMMENT '设备编号',
  equipment_name VARCHAR(128) NOT NULL COMMENT '设备名称',
  status VARCHAR(32) DEFAULT 'normal' COMMENT '设备状态（如正常、故障等）',
  create_by VARCHAR(50),
  create_time DATETIME,
  update_by VARCHAR(50),
  update_time DATETIME,
  del_flag INT DEFAULT 0
) COMMENT='仪器设备';

-- 插入设备数据
INSERT INTO lab_equipment(id, equipment_code, equipment_name, status, create_time)
VALUES
('001', '001', '设备001', 'normal', NOW()),
('002', '002', '设备002', 'normal', NOW()),
('003', '003', '设备003', 'normal', NOW());

-- 项目信息表
CREATE TABLE lab_project (
  id VARCHAR(32) PRIMARY KEY,
  project_no VARCHAR(64) NOT NULL COMMENT '项目编号',
  project_name VARCHAR(200) NOT NULL COMMENT '项目名称',
  start_date DATE COMMENT '开始时间',
  end_date DATE COMMENT '结束时间',
  leader_id VARCHAR(32) COMMENT '项目负责人user_id',
  contract_amount DECIMAL(12,2) COMMENT '合同经费',
  create_by VARCHAR(50),
  create_time DATETIME,
  update_by VARCHAR(50),
  update_time DATETIME,
  del_flag INT DEFAULT 0
) COMMENT='项目信息';

-- 插入项目数据
INSERT INTO lab_project(id, project_no, project_name, start_date, end_date, leader_id, contract_amount, create_time)
VALUES
('p001', 'P001', '项目001', '2023-01-01', '2023-12-31', 'user001', 50000.00, NOW()),
('p002', 'P002', '项目002', '2023-03-01', '2023-06-30', 'user002', 30000.00, NOW());

-- 项目成员表
CREATE TABLE lab_project_member (
  id VARCHAR(32) PRIMARY KEY,
  project_id VARCHAR(32) NOT NULL COMMENT '项目ID',
  user_id VARCHAR(32) NOT NULL COMMENT '成员user_id',
  create_time DATETIME
) COMMENT='项目成员';

-- 插入项目成员数据
INSERT INTO lab_project_member(id, project_id, user_id, create_time)
VALUES
('pm001', 'p001', 'user001', NOW()),
('pm002', 'p001', 'user002', NOW()),
('pm003', 'p002', 'user003', NOW());

-- 使用记录表
CREATE TABLE lab_use_record (
  id VARCHAR(32) PRIMARY KEY,
  equipment_id VARCHAR(32) NOT NULL COMMENT '设备ID',
  user_id VARCHAR(32) NOT NULL COMMENT '使用人user_id',
  start_date DATE NOT NULL COMMENT '开始日期',
  end_date DATE NOT NULL COMMENT '结束日期',
  purpose VARCHAR(500) COMMENT '用途或项目内容',
  actual_hours DECIMAL(8,2) NOT NULL COMMENT '实际使用时间/小时',
  project_id VARCHAR(32) COMMENT '依托项目ID',
  create_by VARCHAR(50),
  create_time DATETIME,
  update_by VARCHAR(50),
  update_time DATETIME,
  del_flag INT DEFAULT 0
) COMMENT='设备使用记录';

-- 插入使用记录数据
INSERT INTO lab_use_record(id, equipment_id, user_id, start_date, end_date, purpose, actual_hours, project_id, create_time)
VALUES
('ur001', '001', 'user001', '2023-04-01', '2023-04-02', '进行设备检测', 8.5, 'p001', NOW()),
('ur002', '002', 'user002', '2023-04-02', '2023-04-02', '项目研究工作', 3.0, 'p002', NOW()),
('ur003', '003', 'user003', '2023-04-03', '2023-04-04', '仪器设备调试', 6.0, 'p001', NOW());