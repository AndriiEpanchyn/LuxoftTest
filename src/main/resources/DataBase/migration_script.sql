-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: textstatistics
-- Source Schemata: textstatistics
-- Created: Wed Jan 19 14:44:10 2022
-- Workbench Version: 8.0.27
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema textstatistics
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `textstatistics` ;
CREATE SCHEMA IF NOT EXISTS `textstatistics` ;

-- ----------------------------------------------------------------------------
-- Table textstatistics.filestatistics
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `textstatistics`.`filestatistics` (
  `file` VARCHAR(255) NULL DEFAULT NULL,
  `lines` INT NULL DEFAULT NULL,
  `longestWord` VARCHAR(255) NULL DEFAULT NULL,
  `shortestWord` VARCHAR(255) NULL DEFAULT NULL,
  `lineLength` INT NULL DEFAULT NULL,
  `averageLength` FLOAT NULL DEFAULT NULL,
  `duplicates` INT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table textstatistics.linestatistics
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `textstatistics`.`linestatistics` (
  `line` VARCHAR(255) NOT NULL,
  `longestWord` VARCHAR(255) NULL DEFAULT NULL,
  `shortestWord` VARCHAR(255) NULL DEFAULT NULL,
  `lineLength` INT NULL DEFAULT NULL,
  `averageLength` FLOAT NULL DEFAULT NULL,
  `duplicates` INT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
SET FOREIGN_KEY_CHECKS = 1;
