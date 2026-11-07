
-- ============================================================
-- File: cleanup.sql
-- Purpose: Remove CDC demo objects from both databases
-- Note: The databases themselves are NOT dropped.
-- Run this script using psql.
-- ============================================================

-- ------------------------------------------------------------
-- Clean up the source database
-- ------------------------------------------------------------
\connect cdc_source

DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS cdc_events;

DROP FUNCTION IF EXISTS capture_employee_changes();

-- ------------------------------------------------------------
-- Clean up the destination database
-- ------------------------------------------------------------
\connect cdc_destination

DROP TABLE IF EXISTS cdc_checkpoint;
DROP TABLE IF EXISTS employees;

-- ------------------------------------------------------------
-- Cleanup completed
-- ------------------------------------------------------------
\echo 'CDC demo cleanup completed successfully.'

