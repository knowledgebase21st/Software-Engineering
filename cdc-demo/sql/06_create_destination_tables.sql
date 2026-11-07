
-- ============================================================
-- File: 06_create_destination_tables.sql
-- Purpose: Create destination employee table
-- Database: cdc_destination
-- ============================================================

-- Create the destination employees table.
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    salary NUMERIC(10,2)
);

-- Verify table creation.
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name = 'employees';

