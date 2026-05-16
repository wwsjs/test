package org.jeecg.modules.demo.lab.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 启动时兜底补齐实验室模块必要字段，避免旧库缺列导致页面查询失败。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LabSchemaBootstrap implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        ensureColumnExists(
                "lab_application",
                "actual_hours",
                "ALTER TABLE `lab_application` ADD COLUMN `actual_hours` DECIMAL(10,2) NULL COMMENT '使用时间' AFTER `purpose`"
        );
        ensureLabUserBindTable();
        repairHistoricalData();
    }

    private void ensureColumnExists(String tableName, String columnName, String alterSql) {
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?",
                Integer.class,
                tableName
        );
        if (tableCount == null || tableCount == 0) {
            log.warn("Schema bootstrap skipped: table {} does not exist in current database.", tableName);
            return;
        }

        Integer columnCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ?",
                Integer.class,
                tableName,
                columnName
        );
        if (columnCount != null && columnCount > 0) {
            return;
        }

        log.warn("Schema bootstrap detected missing column {}.{} , applying DDL.", tableName, columnName);
        jdbcTemplate.execute(alterSql);
        log.info("Schema bootstrap applied DDL successfully for {}.{}", tableName, columnName);
    }

    private void ensureLabUserBindTable() {
        if (!tableExists("lab_user") || !tableExists("sys_user")) {
            return;
        }
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS `lab_user_bind` (" +
                        " `lab_user_id` VARCHAR(64) NOT NULL," +
                        " `sys_user_id` VARCHAR(64) NOT NULL," +
                        " `bind_source` VARCHAR(20) NOT NULL DEFAULT 'auto'," +
                        " `bind_confidence` INT NOT NULL DEFAULT 100," +
                        " `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                        " PRIMARY KEY (`lab_user_id`)," +
                        " KEY `idx_lab_user_bind_sys_user_id` (`sys_user_id`)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实验室成员与系统账号映射表'"
        );
    }

    private boolean tableExists(String tableName) {
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?",
                Integer.class,
                tableName
        );
        return tableCount != null && tableCount > 0;
    }

    private void repairHistoricalData() {
        try {
            int userIdPatched = jdbcTemplate.update(
                    "UPDATE lab_use_record r " +
                            "JOIN lab_application a " +
                            "  ON a.equipment_id = r.equipment_id " +
                            " AND a.project_id = r.project_id " +
                            " AND a.start_date = r.start_date " +
                            " AND a.end_date = r.end_date " +
                            " AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '') " +
                            "SET r.user_id = a.user_id " +
                            "WHERE (r.user_id IS NULL OR r.user_id = '') " +
                            "  AND a.user_id IS NOT NULL " +
                            "  AND a.user_id <> ''"
            );
            int actualHoursPatched = jdbcTemplate.update(
                    "UPDATE lab_application a " +
                            "JOIN lab_use_record r " +
                            "  ON a.equipment_id = r.equipment_id " +
                            " AND a.project_id = r.project_id " +
                            " AND a.start_date = r.start_date " +
                            " AND a.end_date = r.end_date " +
                            " AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '') " +
                            "SET a.actual_hours = r.actual_hours " +
                            "WHERE (a.actual_hours IS NULL OR a.actual_hours <= 0) " +
                            "  AND r.actual_hours IS NOT NULL " +
                            "  AND r.actual_hours > 0"
            );
            int reverseUserIdPatched = jdbcTemplate.update(
                    "UPDATE lab_application a " +
                            "JOIN lab_use_record r " +
                            "  ON a.equipment_id = r.equipment_id " +
                            " AND a.project_id = r.project_id " +
                            " AND a.start_date = r.start_date " +
                            " AND a.end_date = r.end_date " +
                            " AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '') " +
                            "SET a.user_id = r.user_id " +
                            "WHERE (a.user_id IS NULL OR a.user_id = '') " +
                            "  AND r.user_id IS NOT NULL " +
                            "  AND r.user_id <> ''"
            );
            int reverseHoursPatched = jdbcTemplate.update(
                    "UPDATE lab_use_record r " +
                            "JOIN lab_application a " +
                            "  ON a.equipment_id = r.equipment_id " +
                            " AND a.project_id = r.project_id " +
                            " AND a.start_date = r.start_date " +
                            " AND a.end_date = r.end_date " +
                            " AND COALESCE(TRIM(a.purpose), '') = COALESCE(TRIM(r.purpose), '') " +
                            "SET r.actual_hours = a.actual_hours " +
                            "WHERE (r.actual_hours IS NULL OR r.actual_hours <= 0) " +
                            "  AND a.actual_hours IS NOT NULL " +
                            "  AND a.actual_hours > 0"
            );
            int memberIdPatched = jdbcTemplate.update(
                    "UPDATE lab_project SET member_id = leader_id " +
                            "WHERE (member_id IS NULL OR member_id = '') " +
                            "  AND leader_id IS NOT NULL AND leader_id <> ''"
            );

            int bindByUsername = 0;
            int bindByRealname = 0;
            int bindByWorkNo = 0;
            int bindByPhone = 0;
            int bindByEmail = 0;
            int applicationUserNormalized = 0;
            int useRecordUserNormalized = 0;
            if (tableExists("lab_user") && tableExists("sys_user") && tableExists("lab_user_bind")) {
                bindByUsername = jdbcTemplate.update(
                        "INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence) " +
                                "SELECT lu.id, su.id, 'username', 100 " +
                                "FROM lab_user lu JOIN sys_user su ON lu.username = su.username " +
                                "WHERE lu.username IS NOT NULL AND lu.username <> '' " +
                                "ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence)"
                );
                bindByRealname = jdbcTemplate.update(
                        "INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence) " +
                                "SELECT lu.id, su.id, 'realname', 95 " +
                                "FROM lab_user lu JOIN sys_user su ON lu.username = su.realname " +
                                "WHERE lu.username IS NOT NULL AND lu.username <> '' " +
                                "ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence)"
                );
                bindByWorkNo = jdbcTemplate.update(
                        "INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence) " +
                                "SELECT lu.id, su.id, 'work_no', 95 " +
                                "FROM lab_user lu JOIN sys_user su ON lu.work_no = su.work_no " +
                                "WHERE lu.work_no IS NOT NULL AND lu.work_no <> '' " +
                                "ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence)"
                );
                bindByPhone = jdbcTemplate.update(
                        "INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence) " +
                                "SELECT lu.id, su.id, 'phone', 90 " +
                                "FROM lab_user lu JOIN sys_user su ON lu.phone = su.phone " +
                                "WHERE lu.phone IS NOT NULL AND lu.phone <> '' " +
                                "ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence)"
                );
                bindByEmail = jdbcTemplate.update(
                        "INSERT INTO lab_user_bind(lab_user_id, sys_user_id, bind_source, bind_confidence) " +
                                "SELECT lu.id, su.id, 'email', 85 " +
                                "FROM lab_user lu JOIN sys_user su ON lu.email = su.email " +
                                "WHERE lu.email IS NOT NULL AND lu.email <> '' " +
                                "ON DUPLICATE KEY UPDATE sys_user_id = VALUES(sys_user_id), bind_source = VALUES(bind_source), bind_confidence = VALUES(bind_confidence)"
                );
            }
            if (tableExists("lab_application") && tableExists("lab_user_bind")) {
                applicationUserNormalized = jdbcTemplate.update(
                        "UPDATE lab_application a " +
                                "JOIN lab_user_bind b ON a.user_id = b.lab_user_id " +
                                "SET a.user_id = b.sys_user_id " +
                                "WHERE a.user_id <> b.sys_user_id"
                );
            }
            if (tableExists("lab_use_record") && tableExists("lab_user_bind")) {
                useRecordUserNormalized = jdbcTemplate.update(
                        "UPDATE lab_use_record r " +
                                "JOIN lab_user_bind b ON r.user_id = b.lab_user_id " +
                                "SET r.user_id = b.sys_user_id " +
                                "WHERE r.user_id <> b.sys_user_id"
                );
            }

            if (userIdPatched > 0 || actualHoursPatched > 0 || reverseUserIdPatched > 0 || reverseHoursPatched > 0 || memberIdPatched > 0
                    || bindByUsername > 0 || bindByRealname > 0 || bindByWorkNo > 0 || bindByPhone > 0 || bindByEmail > 0
                    || applicationUserNormalized > 0 || useRecordUserNormalized > 0) {
                log.info(
                        "Schema bootstrap repaired data. useRecord.user_id={}, application.actual_hours={}, application.user_id={}, useRecord.actual_hours={}, project.member_id={}, bindByUsername={}, bindByRealname={}, bindByWorkNo={}, bindByPhone={}, bindByEmail={}, application.user_id.normalized={}, useRecord.user_id.normalized={}",
                        userIdPatched, actualHoursPatched, reverseUserIdPatched, reverseHoursPatched, memberIdPatched,
                        bindByUsername, bindByRealname, bindByWorkNo, bindByPhone, bindByEmail, applicationUserNormalized, useRecordUserNormalized
                );
            }
        } catch (Exception ex) {
            log.warn("Schema bootstrap data repair skipped due to exception: {}", ex.getMessage());
        }
    }
}
