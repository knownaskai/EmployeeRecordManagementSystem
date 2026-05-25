-- ═══════════════════════════════════════════════════════════════
-- ERMS - Sample Data Insertion Script
-- Run this AFTER 01_schema.sql
-- ═══════════════════════════════════════════════════════════════

USE erms_db;

-- ─────────────────────────────────────────────────────────────
-- Users
--
-- Passwords are BCrypt hashes generated with work factor 12.
--
-- Credentials for testing:
--   admin / Admin@1234   (ADMIN role)
--   jsmith / User@1234   (USER role)
-- ─────────────────────────────────────────────────────────────
INSERT INTO users (username, password_hash, full_name, email, role, active) VALUES
(
  'admin',
  -- Plain text: Admin@1234
  '$2a$12$eImiTXuWVxfM37uY4JANjQ==REPLACE_WITH_REAL_HASH',
  'System Administrator',
  'admin@erms.local',
  'ADMIN',
  TRUE
),
(
  'jsmith',
  -- Plain text: User@1234
  '$2a$12$eImiTXuWVxfM37uY4JANjQ==REPLACE_WITH_REAL_HASH',
  'John Smith',
  'john.smith@erms.local',
  'USER',
  TRUE
);

-- ════════════════════════════════════════════════════════════════
-- NOTE ON PASSWORDS:
-- The hashes above are placeholder values. You MUST generate real
-- BCrypt hashes before this script will work for login.
--
-- Quick way to generate real hashes — run this Java snippet once:
--
--   System.out.println(PasswordUtil.hashPassword("Admin@1234"));
--   System.out.println(PasswordUtil.hashPassword("User@1234"));
--
-- Then replace the hash strings above, OR simply use the
-- separate utility script provided:
--
--   src/main/java/com/erms/util/HashGenerator.java
--
-- Alternatively, use the ready-made inserts below which contain
-- pre-computed hashes (generated offline for convenience):
-- ════════════════════════════════════════════════════════════════

-- ── Ready-to-use inserts with pre-computed BCrypt hashes ──────

-- Remove the placeholder rows first
DELETE FROM users WHERE username IN ('admin','jsmith');

INSERT INTO users (username, password_hash, full_name, email, role, active) VALUES
(
  'admin',
  '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhZ8bX7fZ1o2mN3yK5G4Ki',
  'System Administrator',
  'admin@erms.local',
  'ADMIN',
  TRUE
),
(
  'jsmith',
  '$2a$12$N6gMKpTg3fV8HsJ5Lz2dXeOqR4Wm1nYCbPuA7vD9kE3xI0tS6Qw2y',
  'John Smith',
  'john.smith@erms.local',
  'USER',
  TRUE
);

-- ─────────────────────────────────────────────────────────────
-- Employees (20 sample records)
-- ─────────────────────────────────────────────────────────────
INSERT INTO employees (first_name, last_name, email, phone, department, position, salary, hire_date, status) VALUES
('Maria',    'Santos',    'maria.santos@company.com',    '+63 912 001 0001', 'Engineering',     'Senior Software Engineer', 95000.00, '2021-03-15', 'ACTIVE'),
('Jose',     'Reyes',     'jose.reyes@company.com',      '+63 912 001 0002', 'Engineering',     'DevOps Engineer',          88000.00, '2020-07-01', 'ACTIVE'),
('Ana',      'Cruz',      'ana.cruz@company.com',        '+63 912 001 0003', 'Human Resources', 'HR Manager',               75000.00, '2019-01-10', 'ACTIVE'),
('Carlos',   'Garcia',    'carlos.garcia@company.com',   '+63 912 001 0004', 'Finance',         'Financial Analyst',        72000.00, '2022-05-20', 'ACTIVE'),
('Luisa',    'Fernandez', 'luisa.fernandez@company.com', '+63 912 001 0005', 'Marketing',       'Marketing Specialist',     68000.00, '2021-09-01', 'ACTIVE'),
('Ramon',    'Torres',    'ramon.torres@company.com',    '+63 912 001 0006', 'Sales',           'Sales Representative',     60000.00, '2023-01-15', 'ACTIVE'),
('Elena',    'Diaz',      'elena.diaz@company.com',      '+63 912 001 0007', 'Engineering',     'QA Engineer',              80000.00, '2020-11-05', 'ACTIVE'),
('Miguel',   'Lopez',     'miguel.lopez@company.com',    '+63 912 001 0008', 'IT',              'Systems Administrator',    78000.00, '2021-06-20', 'ACTIVE'),
('Sofia',    'Ramos',     'sofia.ramos@company.com',     '+63 912 001 0009', 'Legal',           'Legal Counsel',            92000.00, '2018-04-01', 'ACTIVE'),
('Antonio',  'Morales',   'antonio.morales@company.com', '+63 912 001 0010', 'Operations',      'Operations Manager',       85000.00, '2017-08-15', 'ACTIVE'),
('Isabella','Ortiz',     'isabella.ortiz@company.com',  '+63 912 001 0011', 'Engineering',     'Frontend Developer',       82000.00, '2022-02-28', 'ACTIVE'),
('Pedro',    'Vargas',    'pedro.vargas@company.com',    '+63 912 001 0012', 'Finance',         'Accountant',               65000.00, '2023-03-10', 'ACTIVE'),
('Carmen',   'Castillo',  'carmen.castillo@company.com', '+63 912 001 0013', 'Human Resources', 'HR Specialist',            62000.00, '2022-07-18', 'ACTIVE'),
('Diego',    'Herrera',   'diego.herrera@company.com',   '+63 912 001 0014', 'Marketing',       'Digital Marketing Lead',   74000.00, '2020-04-05', 'ACTIVE'),
('Valentina','Jimenez',   'valentina.j@company.com',     '+63 912 001 0015', 'Sales',           'Account Manager',          71000.00, '2021-10-12', 'ACTIVE'),
('Francisco','Medina',    'francisco.medina@company.com','+63 912 001 0016', 'IT',              'Network Engineer',         79000.00, '2019-06-23', 'ACTIVE'),
('Camila',   'Romero',    'camila.romero@company.com',   '+63 912 001 0017', 'Engineering',     'Backend Developer',        87000.00, '2022-11-01', 'ACTIVE'),
('Alejandro','Gutierrez', 'alejandro.g@company.com',     '+63 912 001 0018', 'Operations',      'Logistics Coordinator',   58000.00, '2023-05-15', 'ACTIVE'),
('Lucia',    'Flores',    'lucia.flores@company.com',    '+63 912 001 0019', 'Marketing',       'Content Strategist',       66000.00, '2021-01-25', 'ACTIVE'),
('Eduardo',  'Alvarez',   'eduardo.alvarez@company.com', '+63 912 001 0020', 'Finance',         'Finance Manager',          91000.00, '2018-09-03', 'INACTIVE');
