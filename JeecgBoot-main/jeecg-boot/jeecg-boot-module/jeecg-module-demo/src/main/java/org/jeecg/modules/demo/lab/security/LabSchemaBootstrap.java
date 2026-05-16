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
}
