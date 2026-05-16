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

    private void repairHistoricalData() {
        try {
            int userIdPatched = jdbcTemplate.update(
                    "UPDATE lab_use_record r " +
                            "JOIN lab_application a " +
                            "  ON a.equipment_id = r.equipment_id " +
                            " AND a.project_id = r.project_id " +
                            " AND a.start_date = r.start_date " +
                            " AND a.end_date = r.end_date " +
                            " AND a.purpose = r.purpose " +
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
                            " AND a.purpose = r.purpose " +
                            "SET a.actual_hours = r.actual_hours " +
                            "WHERE a.actual_hours IS NULL " +
                            "  AND r.actual_hours IS NOT NULL"
            );
            if (userIdPatched > 0 || actualHoursPatched > 0) {
                log.info("Schema bootstrap repaired data. patched user_id rows={}, patched actual_hours rows={}",
                        userIdPatched, actualHoursPatched);
            }
        } catch (Exception ex) {
            log.warn("Schema bootstrap data repair skipped due to exception: {}", ex.getMessage());
        }
    }
}
