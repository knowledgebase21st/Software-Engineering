
-- ============================================================
-- File: 02_create_source_tables.sql
-- Purpose: Create source employee and CDC event tables
-- Database: cdc_source
-- ============================================================

-- Create the employees table.
CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    salary NUMERIC(10,2)
);

-- Create the CDC events table.
CREATE TABLE IF NOT EXISTS cdc_events (
    event_id BIGSERIAL PRIMARY KEY,
    operation VARCHAR(10) NOT NULL,
    record_id INTEGER NOT NULL,
    changed_data JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Verify table creation.
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN ('employees', 'cdc_events')
ORDER BY table_name;

