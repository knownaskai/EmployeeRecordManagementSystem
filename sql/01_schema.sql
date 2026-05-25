-- ═══════════════════════════════════════════════════════════════
-- ERMS - Database Schema Creation Script
-- Database: MySQL 8.x
-- Run this script FIRST before inserting sample data.
-- ═══════════════════════════════════════════════════════════════

-- Create (or select) the database
CREATE DATABASE IF NOT EXISTS erms_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE erms_db;

-- ─────────────────────────────────────────────────────────────
-- Table: users
-- Stores application login accounts.
-- Passwords are stored as BCrypt hashes (NEVER plain text).
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    id            INT          NOT NULL AUTO_INCREMENT,
    username      VARCHAR(60)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,   -- BCrypt hash
    full_name     VARCHAR(120) NOT NULL,
    email         VARCHAR(120) NOT NULL,
    role          ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER',
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_users_username (username)
) ENGINE=InnoDB;

-- ─────────────────────────────────────────────────────────────
-- Table: employees
-- Stores employee records managed by the application.
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS employees (
    id            INT          NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(80)  NOT NULL,
    last_name     VARCHAR(80)  NOT NULL,
    email         VARCHAR(120) NOT NULL UNIQUE,
    phone         VARCHAR(30),
    department    VARCHAR(80)  NOT NULL,
    position      VARCHAR(100),
    salary        DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    hire_date     DATE,
    status        ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_emp_last_name  (last_name),
    INDEX idx_emp_department (department),
    INDEX idx_emp_status     (status)
) ENGINE=InnoDB;
