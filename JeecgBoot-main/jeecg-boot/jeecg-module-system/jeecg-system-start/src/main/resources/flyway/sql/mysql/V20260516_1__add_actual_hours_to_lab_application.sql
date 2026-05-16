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
